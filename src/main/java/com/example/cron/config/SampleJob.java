package com.example.cron.config;

import com.example.cron.service.MyService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {

	@Autowired
	MyService myService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		myService.test();
//		String id = jobExecutionContext.getJobDetail().getJobDataMap().get("sakura").toString();
//		System.out.println("执行方法。。。。。" + id + Thread.currentThread().getName());
	}

}
