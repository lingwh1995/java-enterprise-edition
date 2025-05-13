package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_2;

public class TeacherDao {

    public void teach(){
        System.out.println("授课......");
    }

    public String sayName(String name){
        return "My name is" + name;
    }
}
