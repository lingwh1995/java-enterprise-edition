package org.bluebridge.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

/**
 * html转义工具类测试类
 */
@Slf4j
public class HtmlUtilsTest {

    @Test
    public void testHtmlEscape() {
        String html = "<div id=\"testDiv\">test1;test2</div>";
        String escapeHtml = HtmlUtils.htmlEscape(html);
        log.debug("escapeHtml:{}", escapeHtml);
    }

}
