package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串工具类
 */
@Slf4j
public class StringUtilsTest {

    /**
     * 判断字符串是否为空，注意：此方法已经被弃用，现在官方推荐使用 hasLength() 方法
     */
    @Test
    public void testIsEmpty() {
        String str = null;
        boolean result = StringUtils.isEmpty(str);
        log.debug("result : {}", result);

        str = "";
        result = StringUtils.isEmpty(str);
        log.debug("result : {}", result);
    }

    /**
     * 判断字符串是否为空
     */
    @Test
    public void testHasLength() {
        String str = null;
        boolean result = StringUtils.hasLength(str);
        log.debug("result : {}", result);

        str = "";
        result = StringUtils.hasLength(str);
        log.debug("result : {}", result);
    }

    /**
     * 去掉字符串中的所有空格，包括中间的字符
     */
    @Test
    public void testTrimAllWhitespace() {
        // 去掉所有的空格（替换掉所有的空格）
        String s = " a b c d e ";
        s = StringUtils.trimAllWhitespace(s);
        log.debug("s : {}", s);
    }

    /**
     * 判断字符串是否以指定的字符串开头或者结尾，并且忽略大小写
     */
    @Test
    public void testStartsWithAndEndsWith() {
        // 使用jdk原生api判断，没有忽略大小写功能
        String s = "abcde";
        String prefix = "ab";
        String suffix = "de";
        log.debug("使用jdk原生api startsWith : {}", s.startsWith(prefix));
        log.debug("使用jdk原生api endWith : {}", s.endsWith(suffix));

        // 使用spring提供的工具类，有忽略大小写功能
        s = "AbcdE";
        log.debug("使用spring提供的api startsWithIgnoreCase : {}", StringUtils.startsWithIgnoreCase(s, prefix));
        log.debug("使用spring提供的api endsWithIgnoreCase : {}", StringUtils.endsWithIgnoreCase(s, suffix));
    }

    /**
     * 测试将集合元素拼接成字符串
     *  jdk原生api比较spring提供的api更简单一些，java8的stream api最为强大，可以同时设置分隔符前缀和后缀
     */
    @Test
    public void testCollectionToString() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        // 使用jdk原生api实现将集合元素拼接成字符串
        log.debug("使用jdk原生api 集合拼接后的字符串 : {}", String.join(",", list));
        log.debug("使用jdk原生api 集合拼接后的字符串 : {}", String.join("-", list));
        // 使用spring提供的api实现将集合元素拼接成字符串
        log.debug("使用spring提供的api 集合拼接后的字符串 : {}", StringUtils.collectionToCommaDelimitedString(list));
        log.debug("使用spring提供的api 集合拼接后的字符串 : {}", StringUtils.collectionToDelimitedString(list,"-"));

        String[] arr = {"A","B","C"};
        // 使用jdk原生api实现将数组元素拼接成字符串
        log.debug("使用jdk原生api 数组拼接后的字符串 : {}", String.join(",", arr));
        log.debug("使用jdk原生api 数组拼接后的字符串 : {}", String.join("-", arr));
        // 使用spring提供的api实现将数组元素拼接成字符串
        log.debug("使用spring提供的api 数组拼接后的字符串 : {}", StringUtils.arrayToCommaDelimitedString(arr));
        log.debug("使用spring提供的api 数组拼接后的字符串 : {}", StringUtils.arrayToDelimitedString(arr,"-"));

        // 使用jdk8的stream api实现（填入三个参数）
        String result = list.stream().collect(Collectors.joining(", ", "[", "]"));
        log.debug("result: {}", result);
        result = Stream.of(arr).collect(Collectors.joining("- ", "[", "]"));
        log.debug("result: {}", result);

        // 使用jdk8的stream api实现（填入一个参数）
        result = list.stream().collect(Collectors.joining(" * "));
        log.debug("result: {}", result);
        result = Stream.of(arr).collect(Collectors.joining(" | "));
        log.debug("result: {}", result);
    }
}
