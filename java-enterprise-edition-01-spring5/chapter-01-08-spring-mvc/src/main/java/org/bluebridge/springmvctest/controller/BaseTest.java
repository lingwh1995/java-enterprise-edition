package org.bluebridge.springmvctest.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/22 14:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dispatcherservlet.xml"})
@WebAppConfiguration("src/main/webapp")
public class BaseTest {
}
