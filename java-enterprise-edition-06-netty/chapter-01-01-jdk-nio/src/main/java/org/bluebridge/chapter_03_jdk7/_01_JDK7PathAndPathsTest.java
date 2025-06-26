package org.bluebridge.chapter_03_jdk7;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JDK7 新增了 Path 和 Paths 类
 *  Path 用来表示文件路径
 *  Paths 是工具类，用来获取 Path 实例
 */
public class _01_JDK7PathAndPathsTest {

    /**
     * 测试JDK7 新增的 Path 和 Paths 类
     */
    @Test
    public void testJDK7PathAndPaths() {
        // 相对路径
        Path path = Paths.get("1.txt");
        System.out.println(path.toAbsolutePath());

        // 绝对路径1
        path = Paths.get("d:\\1.txt");
        System.out.println(path.toAbsolutePath());

        // 绝对路径2
        path = Paths.get("d:/1.txt");
        System.out.println(path.toAbsolutePath());

        // 第二个参数可以是文件名
        path = Paths.get("d:/a", "1.txt");
        System.out.println(path.toAbsolutePath());
        // 第二个参数也可以是文件夹
        path = Paths.get("d:/a", "b");
        System.out.println(path.toAbsolutePath());

        // 正常化路径
        path = Paths.get("d:\\data\\projects\\a\\..\\b");
        System.out.println(path);
        System.out.println(path.normalize());
    }

}
