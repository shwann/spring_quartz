package com.shwan.hsguo.service;

import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shwan.hsguo.common.spring.SpringContextHolder;
public class TestQuartzServiceJob implements Serializable{
	
	private Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	private static final long serialVersionUID = -5925155138587056423L;
	
	public void execute() {
		long start = new Date().getTime();
		log.info("== Sync Video Timer Job Start ==");
		TestQuartzService service = (TestQuartzService)SpringContextHolder.getBean("testQuartzService");
		service.execute();
		long end = new Date().getTime();
		log.info("== Sync Video Timer Job End, Cost: "+(end-start)/1000+"s ==");
	}

}
