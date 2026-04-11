package org.bluebridge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author ronin
 */
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("容器启动......");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("容器销毁......");
    }
}
