package com.example.shiro.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.example.shiro.aspectj.annotation.Log;
import com.example.shiro.constants.enumconstants.BusinessStatus;
import com.example.shiro.entity.OperLog;
import com.example.shiro.entity.User;
import com.example.shiro.utils.AddressUtils;
import com.example.shiro.utils.IpUtils;
import com.example.shiro.utils.ServletUtils;
import com.example.shiro.utils.StringUtils;

import com.example.shiro.utils.manager.AsyncManager;
import com.example.shiro.utils.manager.factory.AsyncFactory;
import com.example.shiro.utils.security.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    AsyncManager asyncManager;
    // 配置织入点
    @Pointcut("@annotation(com.example.shiro.aspectj.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 后置返回通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e){
        try {
            //获得注解
            Log annotationLog = getAnnotationLog(joinPoint);
            if (annotationLog==null){
                return;
            }
            //得到当前用户
            User currentUser = ShiroUtils.getSysUser();

            //*===========数据库日志===========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            //请求地址

            String ip = ShiroUtils.getIp();

            operLog.setOperIp(ip);

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (currentUser!=null){
                //设置登录用户名
                operLog.setOperParam(currentUser.getLastName());
            }
            if (e!=null){
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(),0,2000));
            }
            //设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className+"."+methodName+"()");
            // 处理设置注解上的参数
            getMethodDescription(annotationLog, operLog);
        }catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }

    }

    private void getMethodDescription(Log annotationLog, OperLog operLog) {
        //设置action动作
        operLog.setBusinessType(annotationLog.businessType().ordinal());
        //设置标题
        operLog.setTitle(annotationLog.title());
        //是否需要保存request参数
        if (annotationLog.isSaveRequestData()){
            setRequestValue(operLog);
        }
        // 保存数据库
      asyncManager.execute(AsyncFactory.recordOper(operLog));
    }
    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog
     *
     */
    private void setRequestValue(OperLog operLog)
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }
    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
