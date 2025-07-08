package org.bluebridge.ioc.beanlifecycle.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/10 11:19
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
