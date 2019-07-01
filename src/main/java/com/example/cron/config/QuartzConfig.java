package com.example.cron.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
public class QuartzConfig {

	// 注入service需要配置工厂类
	@Autowired
	private JobFactory jobFactory;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();

		// 注入service需要配置工厂类
		factory.setJobFactory(jobFactory);

		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		//在quartz.properties中的属性被读取并注入后再初始化对象
		propertiesFactoryBean.afterPropertiesSet();
		Properties properties = propertiesFactoryBean.getObject();

		factory.setQuartzProperties(properties);
		return factory;
	}

	/*
	 * quartz初始化监听器
	 */
//	@Bean
	public QuartzInitializerListener executorListener() {
		return new QuartzInitializerListener();
	}

	/*
	 * 通过SchedulerFactoryBean获取Scheduler的实例
	 */
	@Bean
	public Scheduler scheduler() throws Exception {
		Scheduler scheduler = schedulerFactoryBean().getScheduler();
		scheduler.start();
		return scheduler;
	}

}
