package com.shwan.hsguo.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestQuartzService {
	private Logger log = LoggerFactory.getLogger(this.getClass()); 
	public void execute() {
		test();
	}
	private static int num;
	static{
		num = 0;
	}
	@SuppressWarnings("unchecked")
	public void test(){
		log.info("test:执行业务逻辑"+num++);
	}
}
