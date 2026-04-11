package org.bluebridge.converter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lingwh
 * @desc   SpringMVC自定义类型转换器,Converter<S,T>:原数据类型，目标数据类型
 * @date   2019/6/17 14:22
 */

/**
 *  步骤:
 *      1.编写前台代码，把日期格式的字符串传递到后台
 *      2.编写转换器
 *      3.注册转换器
 *          配置注解驱动，并将转换器配置到注解驱动中:<mvc:annotation-driven/>
 *          配置转换器
 *      4.在Controller层接收参数的时候采用如下格式写法即可:@RequestParam("time") Date date
 */
public class DateConverter implements Converter<String,Date>{

    @Nullable
    @Override
    public Date convert(String source) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(source);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
