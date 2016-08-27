package com.shwan.hsguo.common.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ReflectionUtils;

import com.shwan.hsguo.common.annotation.Properties;



/**
 * 继承PropertyPlaceholderConfigurer并实现BeanPostProcessor,InitializingBean接口
 * 通过Annotation标注变量来实现Property配置项的自动注入
 * 
 * @author phily.lan (hsguo@youku.com)
 */

public class PropertiesAnnotationBeanPostProcessor extends PropertyPlaceholderConfigurer implements
		BeanPostProcessor, InitializingBean {

	private static transient Logger logger = Logger.getLogger(PropertiesAnnotationBeanPostProcessor.class);
	
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	private java.util.Properties pros;

	// 指定Annotation标注的变量的对应的类型只有 String 和 Integer类
	private Class[] enableClassList = {String.class, Integer.class};

	public void setEnableClassList(Class[] enableClassList) {
		this.enableClassList = enableClassList;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// Java反射获得Bean的所有变量
		Field[] subField = bean.getClass().getDeclaredFields();
		Field[] superField = new Field[0];

		// 判断是否有父类,有的话获得父类的所有变量
		if (bean.getClass().getSuperclass() != null) {
			superField = bean.getClass().getSuperclass().getDeclaredFields();
		}

		Field[] fields = new Field[subField.length + superField.length];
		System.arraycopy(subField, 0, fields, 0, subField.length);
		System.arraycopy(superField, 0, fields, subField.length, superField.length);
		for (Field field : fields) {
			if (field.isAnnotationPresent(Properties.class)) {
				// static 变量不能注入
				if (Modifier.isStatic(field.getModifiers())) {
					throw new IllegalStateException("@Property annotation is not supported on static fields");
				}

				Properties p = field.getAnnotation(Properties.class);
				String key = p.key().length()>0? p.key() : (bean.getClass().getName()+"."+field.getName());
				String value = pros.getProperty(key);
				try {
					if(p.required() && value == null){
						throw new NullPointerException(bean.getClass().getSimpleName() + "." + field.getName()
														+ "is requred,but not been configured");
					}else{
						// 设置变量可以被访问
						ReflectionUtils.makeAccessible(field);
						// 简单类型转换
						Object _value = typeConverter.convertIfNecessary(value, field.getType());
						field.set(bean, _value);
					}
				}
				catch (Exception e) {
					logger.error("Error: BeanCreateException happen when create " + beanName, e);
				}
				if (filterType(field.getType().toString())) {}
			}
		}

		return bean;
	}

	/**
	 * 校验被标注的变量是否在指定的类型范围,只有指定类型的变量才会被处理 对于*.propreties配置一般对应成String 和 Integer
	 */
	@SuppressWarnings("unchecked")
	private boolean filterType(String type) {

		if (type != null) {
			for (Class c : enableClassList) {
				if (c.toString().equals(type)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	public void afterPropertiesSet() throws Exception {
		pros = mergeProperties();
	}
}
