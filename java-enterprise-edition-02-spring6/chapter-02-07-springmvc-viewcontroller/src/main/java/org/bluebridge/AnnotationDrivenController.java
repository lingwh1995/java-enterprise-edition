package org.bluebridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/viewController")
@Controller
public class AnnotationDrivenController {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationDrivenController.class);

    /**
     * 测试<mvc:annotation-driven/>配置
     * @return
     */
    @RequestMapping("/testAnnotationDriven")
    public String testAnnotationDriven() {
        logger.info("跳转到success页面...");
        return "success";
    }
}
