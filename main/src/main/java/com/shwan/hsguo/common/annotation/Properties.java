package com.shwan.hsguo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于Annotation和 Spring BeanPostProcessor实现配置文件全局注入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) 
public @interface Properties {
	String key() default "";
	boolean required() default true;
}

