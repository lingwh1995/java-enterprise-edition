package org.bluebridge.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ronin
 */
@ControllerAdvice
public class MyExceptionHandler {

//    /**
//     * 这个版本不管是浏览器还是postman访问都会返回json格式的错误信息,不会自动识别浏览器和postman
//     * version 1.0
//     * @param e
//     * @return
//     */
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String,Object> handlerException(Exception e){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("code","there is a exception");
//        map.put("message", e.getMessage());
//        return map;
//    }

    /**
     * 会自动识别浏览器和postman发出的请求
     * version 2.0
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    public String handlerException(Exception e, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        map.put("code","there is a exception");
        map.put("message", e.getMessage());
        request.setAttribute("javax.servlet.error.status_code",500);
        request.setAttribute("ext", map);
        return "forward:/error";
    }
}
