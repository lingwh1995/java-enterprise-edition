package org.bluebridge.mapreduce.unit_13_my_distcp;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileChecksum;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 模拟 hadoop 自带 distcp 命令的 CopyMapper：真正的拷贝动作在这里完成
 *
 * 输入是拷贝清单（每行：源文件路径\t目标文件路径），每个 MapTask 拿到清单的一部分，
 * 逐行执行文件拷贝，从而实现"分布式拷贝"的核心思想
 *
 * @author lingwh
 * @date 2026/8/28 14:45
 */
@Slf4j
public class CopyMapper extends Mapper<LongWritable, Text, Text, Text> {

    /** 拷贝缓冲区大小 */
    private static final int BUFFER_SIZE = 8 * 1024;

    private Configuration conf;
    private boolean overwrite;
    private boolean skipCrc;
    private final byte[] buffer = new byte[BUFFER_SIZE];

    /**
     * Mapper 初始化：从 Job 配置中读取 distcp 选项
     */
    @Override
    protected void setup(Context context) {
        conf = context.getConfiguration();
        overwrite = conf.getBoolean("distcp.overwrite", false);
        skipCrc = conf.getBoolean("distcp.skipcrc", false);
        log.info("执行链路 - CopyMapper.setup() 初始化完成, overwrite: {}, skipCrc: {}", overwrite, skipCrc);
    }

    /**
     * 读取拷贝清单中的一行（源文件路径\t目标文件路径），执行文件拷贝
     *
     * @param key   行偏移量
     * @param value 拷贝清单行：源文件路径\t目标文件路径
     * @param context 上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1. 解析拷贝清单行
        String[] paths = value.toString().split("\t", -1);
        if (paths.length != 2) {
            log.warn("执行链路 - 拷贝清单行格式不正确, 跳过: {}", value);
            return;
        }
        Path sourcePath = new Path(paths[0]);
        Path targetPath = new Path(paths[1]);
        log.info("执行链路 - 开始拷贝: {} --> {}", sourcePath, targetPath);

        FileSystem sourceFS = sourcePath.getFileSystem(conf);
        FileSystem targetFS = targetPath.getFileSystem(conf);

        // 2. 目标已存在且未指定 -overwrite 时跳过（对应真实 distcp 默认不覆盖的行为）
        if (targetFS.exists(targetPath) && !overwrite) {
            log.info("执行链路 - 目标文件已存在, 跳过: {}", targetPath);
            context.getCounter("MyDistCp", "SKIP").increment(1);
            context.write(new Text("SKIP"), new Text(sourcePath + " --> " + targetPath));
            return;
        }

        try {
            // 3. 创建目标父目录
            Path parent = targetPath.getParent();
            if (parent != null) {
                targetFS.mkdirs(parent);
            }

            // 4. 以缓冲区循环读写完成文件拷贝
            long bytesCopied = 0;
            try (InputStream in = sourceFS.open(sourcePath);
                 OutputStream out = targetFS.create(targetPath, true)) {
                int read;
                while ((read = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, read);
                    bytesCopied += read;
                }
            }

            // 5. 未跳过 CRC 时校验源与目标校验和
            if (!skipCrc) {
                verifyCrc(sourcePath, targetPath, sourceFS, targetFS);
            }

            context.getCounter("MyDistCp", "COPY").increment(1);
            context.getCounter("MyDistCp", "BYTES").increment(bytesCopied);
            context.write(new Text("COPY"), new Text(sourcePath + " --> " + targetPath));
            log.info("执行链路 - 拷贝完成: {} --> {}, 字节数: {}", sourcePath, targetPath, bytesCopied);
        } catch (IOException e) {
            // 单个文件拷贝失败不影响其他文件（对应真实 distcp 的 -i 忽略失败思想）
            log.error("执行链路 - 拷贝失败: {} --> {}, 原因: {}", sourcePath, targetPath, e.getMessage());
            context.getCounter("MyDistCp", "FAIL").increment(1);
            context.write(new Text("FAIL"), new Text(sourcePath + " --> " + targetPath + ", " + e.getMessage()));
        }
    }

    /**
     * 校验源文件与目标文件的校验和是否一致
     *
     * @param source   源路径
     * @param target   目标路径
     * @param sourceFS 源文件系统
     * @param targetFS 目标文件系统
     * @throws IOException 校验和不一致时抛出
     */
    private void verifyCrc(Path source, Path target, FileSystem sourceFS, FileSystem targetFS)
            throws IOException {
        FileChecksum sourceChecksum = sourceFS.getFileChecksum(source);
        if (sourceChecksum == null) {
            log.warn("执行链路 - 无法获取源文件校验和(文件系统可能不支持), 跳过校验: {}", source);
            return;
        }
        FileChecksum targetChecksum = targetFS.getFileChecksum(target);
        if (targetChecksum == null) {
            throw new IOException("Checksum error: unable to retrieve checksum for " + target);
        }
        if (!sourceChecksum.equals(targetChecksum)) {
            throw new IOException("Checksum mismatch between " + source + " and " + target);
        }
    }
}
