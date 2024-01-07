package org.bluebridge.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

@RequestMapping("/resources/")
@Controller
public class ResourceController {

    /**
     * 延迟获取vue
     * @return
     */
    @ResponseBody
    @RequestMapping("/delayGetVue/{delay}")
    public String delayGetVue(@PathVariable("delay") Integer delay,HttpSession session) throws IOException, InterruptedException {
        Thread.sleep(delay * 1000);
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath(new StringBuilder("/static/vue.js").toString());
        return FileUtils.readFileToString(new File(realPath));
    }
}
