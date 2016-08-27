package com.shwan.hsguo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *@author  hsguo@youku.com 
 *@date 创建时间：2016-7-29 下午6:30:45 
 *@version 1.0 
 *@parameter 
 *@return
 */
public class QuartzMain {

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		springStart();
	}
	/**
	 * 执行定时器
	 */
	public static void springStart() {
		try {
			String[] locations = {
					//加载spring配置文件
					"classpath:spring/config/spring-context.xml",
					//加载quartz配置文件
					"classpath:spring/quartz/spring-quartz.xml"};
			//执行加载配置文件
			ApplicationContext context = new ClassPathXmlApplicationContext(locations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
