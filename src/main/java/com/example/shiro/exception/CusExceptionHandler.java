package com.example.shiro.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CusExceptionHandler {


    @ExceptionHandler({UserNotExistsException.class,UserPasswordNotMatchException.class
                     ,UserPasswordRetryLimitExceedException.class, AuthorizationException.class})
    public  String handlerException(Exception e, HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",500);
        Map<String,Object> map=new HashMap<>();
        //map.put("code","usernotexists");
        map.put("message",e.getMessage());
        request.setAttribute("ext",map);
        return "forward:/error";
    }
}
