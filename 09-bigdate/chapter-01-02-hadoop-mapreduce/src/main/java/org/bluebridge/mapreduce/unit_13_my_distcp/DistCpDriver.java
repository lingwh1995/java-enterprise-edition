package org.bluebridge.mapreduce.unit_13_my_distcp;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟 hadoop 自带 distcp 命令（分布式拷贝）核心思想的 Driver 类
 *
 * distcp 的核心思想：
 * 1. 先把所有需要拷贝的文件整理成一份"拷贝清单"（copy listing）
 * 2. 提交一个 map-only 的 MapReduce 作业
 * 3. 每个 MapTask 读取拷贝清单的一部分，并行地把文件从源文件系统拷贝到目标文件系统
 *
 * 本示例为了演示核心思想做了大量简化：
 * - 只支持单个源路径
 * - 拷贝清单用普通文本文件表示，每行一个：源文件路径\t目标文件路径
 * - 只支持 -overwrite（覆盖已存在文件）和 -skipcrccheck（跳过 CRC 校验）两个选项
 * - 真实 hadoop 的 distcp 还支持 -update / -delete / -append / -i / -m / -p 等，详见官方文档
 *
 * 本地 IDEA 测试模式（不传命令行参数）：
 * 源目录: target/classes/hadoop/input/unit_13_my_distcp
 * 目标目录: target/classes/hadoop/output/unit_13_my_distcp
 *
 * @author lingwh
 * @date 2026/8/28 14:30
 */
@Slf4j
public class DistCpDriver {

    private static final Random RANDOM = new Random();

    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        log.info("执行链路 - 开始执行 DistCpDriver.main()......");

        // 1. 解析命令行参数：支持 -overwrite / -skipcrccheck，最后一个参数是目标路径，其余是源路径
        boolean overwrite = false;
        boolean skipCrc = false;
        List<String> pathArgs = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "-overwrite":
                    overwrite = true;
                    break;
                case "-skipcrccheck":
                    skipCrc = true;
                    break;
                default:
                    if (arg.startsWith("-")) {
                        System.err.println("Unknown argument: " + arg);
                        usage();
                        System.exit(-1);
                    }
                    pathArgs.add(arg);
            }
        }

        Configuration conf = new Configuration();
        Path sourcePath;
        Path targetPath;
        if (pathArgs.size() == 2) {
            // 命令行运行方式：hadoop jar xx.jar DistCpDriver [-overwrite] <源路径> <目标路径>
            sourcePath = new Path(pathArgs.get(0));
            targetPath = new Path(pathArgs.get(1));
        } else if (pathArgs.size() > 2) {
            System.err.println("本简化示例只支持单个源路径");
            usage();
            System.exit(-1);
            return;
        } else {
            // 本地 IDEA 测试模式：使用默认路径
            Path basePath = new Path(DistCpDriver.class.getClassLoader().getResource("").toURI());
            sourcePath = new Path(basePath, "hadoop/input/unit_13_my_distcp");
            targetPath = new Path(basePath, "hadoop/output/unit_13_my_distcp");
            log.info("执行链路 - 未检测到命令行参数, 使用本地测试默认路径, 源: {}, 目标: {}", sourcePath, targetPath);
            // 本地测试模式自动删除目标目录，保证重复运行结果一致
            FileSystem targetFS = targetPath.getFileSystem(conf);
            if (targetFS.exists(targetPath)) {
                targetFS.delete(targetPath, true);
            }
        }
        conf.setBoolean("distcp.overwrite", overwrite);
        conf.setBoolean("distcp.skipcrc", skipCrc);
        log.info("执行链路 - distcp 参数, 源: {}, 目标: {}, overwrite: {}, skipCrc: {}",
                sourcePath, targetPath, overwrite, skipCrc);

        // 2. 源路径存在性检查
        FileSystem sourceFS = sourcePath.getFileSystem(conf);
        if (!sourceFS.exists(sourcePath)) {
            System.err.println("源路径不存在: " + sourcePath);
            System.exit(1);
        }

        // 3. 生成拷贝清单（在目标路径所在文件系统下创建临时工作目录）
        FileSystem workFS = targetPath.getFileSystem(conf);
        Path workDir = new Path(targetPath.getParent(),
                "_distcp_tmp_" + targetPath.getName() + "_" + RANDOM.nextInt(Integer.MAX_VALUE));
        try {
            workFS.mkdirs(workDir);
            Path listingFile = new Path(workDir, "copylist.txt");
            createCopyListing(conf, sourcePath, targetPath, listingFile);

            // 4. 创建 map-only 作业：Mapper 从拷贝清单读取 (源文件, 目标文件) 并执行拷贝
            Job job = Job.getInstance(conf, "my distcp");
            job.setJarByClass(DistCpDriver.class);
            job.setMapperClass(CopyMapper.class);
            job.setNumReduceTasks(0);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, listingFile);
            FileOutputFormat.setOutputPath(job, new Path(workDir, "_logs"));

            // 5. 提交作业并等待完成
            boolean success = job.waitForCompletion(true);
            System.exit(success ? 0 : 1);
        } finally {
            // 6. 清理临时工作目录
            workFS.delete(workDir, true);
            log.info("执行链路 - 已清理临时工作目录: {}", workDir);
        }
        log.info("执行链路 - 结束执行 DistCpDriver.main()......");
    }

    /**
     * 生成拷贝清单：递归遍历源路径，把每个文件写成一行 源文件路径\t目标文件路径
     *
     * @param conf        Hadoop 配置
     * @param sourcePath  源路径
     * @param targetPath  目标路径
     * @param listingFile 拷贝清单文件路径
     * @throws IOException 生成失败时抛出
     */
    private static void createCopyListing(Configuration conf, Path sourcePath, Path targetPath,
            Path listingFile) throws IOException {
        log.info("执行链路 - 开始生成拷贝清单: {}", listingFile);
        FileSystem sourceFS = sourcePath.getFileSystem(conf);
        FileStatus sourceStatus = sourceFS.getFileStatus(sourceFS.makeQualified(sourcePath));
        // 目标路径补全 scheme/authority，保证目标路径完整
        Path targetQualified = targetPath.getFileSystem(conf).makeQualified(targetPath);

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(listingFile.getFileSystem(conf).create(listingFile), StandardCharsets.UTF_8))) {
            if (sourceStatus.isDirectory()) {
                // 源是目录：目录下的所有文件按相对路径映射到目标目录下
                walkAndWriteListing(writer, sourceFS, sourceStatus, sourceStatus.getPath(), targetQualified);
            } else {
                // 源是文件：直接拷贝到目标路径
                writer.write(sourceStatus.getPath() + "\t" + targetQualified + "\n");
            }
        }
        log.info("执行链路 - 拷贝清单生成完成: {}", listingFile);
    }

    /**
     * 递归遍历目录，将每个文件以 源文件路径\t目标文件路径 的形式写入拷贝清单
     *
     * @param writer     拷贝清单写入器
     * @param sourceFS   源文件系统
     * @param status     当前目录状态
     * @param sourceRoot 源根路径（用于计算相对路径）
     * @param targetPath 目标路径
     * @throws IOException 写入失败时抛出
     */
    private static void walkAndWriteListing(Writer writer, FileSystem sourceFS, FileStatus status,
            Path sourceRoot, Path targetPath) throws IOException {
        FileStatus[] children = sourceFS.listStatus(status.getPath());
        for (FileStatus child : children) {
            if (child.isDirectory()) {
                walkAndWriteListing(writer, sourceFS, child, sourceRoot, targetPath);
            } else {
                // 相对路径 = 源文件路径 - 源根路径，例如 /a.txt 或 /sub/b.txt
                String relPath = child.getPath().toString().substring(sourceRoot.toString().length());
                writer.write(child.getPath() + "\t" + new Path(targetPath.toString() + relPath) + "\n");
            }
        }
    }

    /**
     * 打印使用说明
     */
    private static void usage() {
        System.err.println("Usage: hadoop jar xx.jar org.bluebridge.mapreduce.unit_13_my_distcp.DistCpDriver " +
                "[-overwrite] [-skipcrccheck] <源路径> <目标路径>");
    }
}
