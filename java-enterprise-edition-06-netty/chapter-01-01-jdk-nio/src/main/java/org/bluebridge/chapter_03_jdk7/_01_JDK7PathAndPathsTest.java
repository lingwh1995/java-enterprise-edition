package org.bluebridge.chapter_03_jdk7;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lingwh
 * @desc   JDK7新增的Path和Paths类（Path用来表示文件路径、Paths是工具类，用来获取Path实例）
 * @date   2025/6/25 16:05
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
