package org.summerframework.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{

    private Logger logger = LoggerFactory.getLogger(ClassPathXmlApplicationContext.class);

    //用于存放xml配置文件中解析出来的bean信息
    private Map<String,Object> singletonObject = new HashMap<>();

    public ClassPathXmlApplicationContext(String configureLocation) throws DocumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getResourceAsStream(configureLocation));
        logger.info("获取到的document对象：" + document.toString());
        //获取所有的bean并存放在map中
        List<Node> nodes = document.selectNodes("//bean");
        Iterator<Node> iteratorBean = nodes.iterator();
        while (iteratorBean.hasNext()) {
            Element node = (Element) iteratorBean.next();
            String beanId = node.attributeValue("id");
            logger.info("beanId：" + beanId);
            String beanClassName = node.attributeValue("class");
            logger.info("beanClassName：" + beanClassName);
            //使用反射技术创建对象
            Class<?> aClass = Class.forName(beanClassName);
            //获取无参构造方法
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            //使用无参构造方法创建对象
            Object bean = constructor.newInstance();
            //把创建的bean对象放入Map中
            singletonObject.put(beanId,bean);
        }
        logger.info(singletonObject.toString());

        Iterator<Node> iteratorAttribute = nodes.iterator();
        while (iteratorAttribute.hasNext()) {
            Element node = (Element) iteratorAttribute.next();
            List<Element> properties = node.elements("property");
            Iterator<Element> iteratorProperties = properties.iterator();

            String beanId = node.attributeValue("id");
            logger.info("beanId：" + beanId);
            String beanClassName = node.attributeValue("class");
            logger.info("beanClassName：" + beanClassName);

            while (iteratorProperties.hasNext()) {
                Element property = iteratorProperties.next();
                //获取name属性的值
                String namePropertyValue = property.attributeValue("name");
                logger.info("namePropertyValue：" + namePropertyValue);
                //使用反射技术创建对象
                Class<?> aClass = Class.forName(beanClassName);
                //获取setter方法名
                String setterMethodName = "set" + namePropertyValue.toUpperCase().charAt(0)
                        + namePropertyValue.substring(1,namePropertyValue.length());
                logger.info("setterMethodName：" + setterMethodName);
                //根据属性的值获取属性类型
                Field field = aClass.getDeclaredField(namePropertyValue);
                //并且将方法名作为参数封装到Method对象中
                Method method = aClass.getDeclaredMethod(setterMethodName, field.getType());
                //获取无参构造方法
                Constructor<?> constructor = aClass.getDeclaredConstructor();
                //使用无参构造创建对象
                Object bean = constructor.newInstance();
                //获取ref属性的值，当ref属性的值不为空，则说明该属性是非简单类型数据
                String refPropertyValue = property.attributeValue("ref");
                logger.info("refPropertyValue：" + refPropertyValue);
                if(refPropertyValue != null) {
                    //从Map中根据ref属性获取对应的bean，并且作为方法参数传递进去
                    method.invoke(singletonObject.get(beanId),singletonObject.get(refPropertyValue));
                }
                //获取value属性的值，当value属性的值不为空，则说明该属性是简单类型数据
                String valuePropertyValue = property.attributeValue("value");
                logger.info("valuePropertyValue：" + valuePropertyValue);

                if(valuePropertyValue != null) {
                    String propertyType = field.getType().getSimpleName();
                    Object valuePropertyValueWithType = null;
                    switch (propertyType) {
                        case "byte", "Byte":
                            valuePropertyValueWithType = Byte.valueOf(valuePropertyValue);
                            break;
                        case "short", "Short":
                            valuePropertyValueWithType = Short.valueOf(valuePropertyValue);
                            break;
                        case "int", "Integer":
                            valuePropertyValueWithType = Integer.valueOf(valuePropertyValue);
                            break;
                        case "long", "Long":
                            valuePropertyValueWithType = Long.valueOf(valuePropertyValue);
                            break;
                        case "float", "Float":
                            valuePropertyValueWithType = Float.valueOf(valuePropertyValue);
                            break;
                        case "double", "Double":
                            valuePropertyValueWithType = Double.valueOf(valuePropertyValue);
                            break;
                        case "boolean", "Boolean":
                            valuePropertyValueWithType = Boolean.valueOf(valuePropertyValue);
                            break;
                        case "char", "Character":
                            valuePropertyValueWithType = Character.valueOf(valuePropertyValue.charAt(0));
                            break;
                        default:
                            //当参数数据类型类型为String时走default
                            valuePropertyValueWithType = valuePropertyValue;
                            break;
                    }
                    //调用setter方法把value属性的值赋给属性
                    method.invoke(singletonObject.get(beanId),valuePropertyValueWithType);
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return singletonObject.get(beanName);
    }

    public static InputStream getResourceAsStream(String configureLocation){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(configureLocation);
    }

}
