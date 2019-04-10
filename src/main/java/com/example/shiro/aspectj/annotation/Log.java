package com.example.shiro.aspectj.annotation;

import com.example.shiro.constants.enumconstants.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author ruoyi
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 操作标题
     * @return
     */
    public String title() default "";

    /**
     * 功能类型
     * @return
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求参数
     * @return
     */
    public boolean isSaveRequestData() default true;
}
