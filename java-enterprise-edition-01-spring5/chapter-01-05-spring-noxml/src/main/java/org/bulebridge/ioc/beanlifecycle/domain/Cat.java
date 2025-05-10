package org.bulebridge.ioc.beanlifecycle.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author ronin
 */
public class Cat implements InitializingBean,DisposableBean{
    public Cat(){
        System.out.println("Cat Constructor......");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat......afterPropertiesSet......");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat......destroy......");
    }
}
