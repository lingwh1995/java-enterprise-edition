package org.bluebridge.chapter_01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JDK7 新增了 Files 类
 */
@Slf4j
public class _11_JDK7FilesTest {

    /**
     * 测试JDK7 新增的 Files 类基础用法
     */
    @Test
    public void testJDK7FilesBasic() throws IOException {
        // 检查文件是否存在
        Path path = Paths.get("files/files.txt");
        System.out.println(path.toAbsolutePath());
        System.out.println(Files.exists(path));

        /**
         * 创建一级目录
         *  如果目录已存在，会抛异常 FileAlreadyExistsException
         *  不能一次创建多级目录，否则会抛异常 NoSuchFileException
         */
//        path = Paths.get("files/d1");
//        System.out.println(path.toAbsolutePath());
//        Files.createDirectory(path);

        /**
         * 创建多级目录用
         */
        path = Paths.get("files/d1/d2");
        Files.createDirectories(path);
    }

    /**
     * 测试JDK7 新增的 Files 类拷贝文件
     *  如果文件已存在，会抛异常 FileAlreadyExistsException
     *  如果希望用 source 覆盖掉 target，需要用 StandardCopyOption 来控制
     *  Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
     */
    @Test
    public void testJDK7FilesCopyFile() throws IOException {
        Path source = Paths.get("files/files-copy.txt");
        Path target = Paths.get("files/target-copy.txt");
        //Files.copy(source, target);
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 测试JDK7 新增的 Files 类移动文件
     *  如果文件已存在，会抛异常 FileAlreadyExistsException
     *  StandardCopyOption.ATOMIC_MOVE 保证文件移动的原子性
     *  Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
     */
    @Test
    public void testJDK7FilesMoveFile() throws IOException {
        Path source = Paths.get("files/files-move.txt");
        Path target = Paths.get("files/target-move.txt");
        //Files.move(source, target);
        Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * 测试JDK7 新增的 Files 类删除文件
     *  如果文件不存在，会抛异常 NoSuchFileException
     */
    @Test
    public void testJDK7FilesDeleteFile() throws IOException {
        Path target = Paths.get("files/target-copy.txt");
        Files.delete(target);
    }

    /**
     * 测试JDK7 新增的 Files 类删除文件夹
     *  如果目录还有内容，会抛异常 DirectoryNotEmptyException
     */
    @Test
    public void testJDK7FilesDeleteDir() throws IOException {
        Path target = Paths.get("files/d1");
        Files.delete(target);
    }

    /**
     * 测试JDK7 新增的 Files 类统计文件和文件夹数量
     */
    @Test
    public void testJDK7FilesListFiles() throws IOException {
        Path path = Paths.get("D:\\software\\develop\\jdk\\jdk1.8");
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount);
        System.out.println(fileCount);
    }

    /**
     * 测试JDK7 新增的 Files 类统计jar包数量
     */
    @Test
    public void testJDK7FilesCountJarFiles() throws IOException {
        Path path = Paths.get("D:\\software\\develop\\jdk\\jdk1.8");
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (file.toFile().getName().endsWith(".jar")) {
                    fileCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(fileCount);
    }

    /**
     * 测试JDK7 新增的 Files 类删除多级目录
     */
    @Test
    public void testJDK7FilesDeleteDirs() throws IOException {
        Path path = Paths.get("files/d1");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    /**
     * // TODO 这个方法写的有问题，有时间了改正这个方法
     * 测试JDK7 新增的 Files 类拷贝多级目录
     */
    @Test
    public void testJDK7FilesCopyDirs() throws IOException {
        long start = System.currentTimeMillis();
        Path sourceDir = Paths.get("files/d1");
        Path targetDir = Paths.get("files/d11");
        Files.walk(sourceDir).forEach(path -> {
            try {
                //String targetName = path.toString().replace(sourceDir, targetDir);
                if (Files.isDirectory(path)) { // 是目录
                    Files.copy(sourceDir, targetDir, StandardCopyOption.REPLACE_EXISTING);
                }else if (Files.isRegularFile(path)) { // 是普通文件
                    Files.copy(sourceDir, targetDir, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
