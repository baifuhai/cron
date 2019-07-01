package com.example.cron.config;

import com.example.cron.dao.JobDao;
import com.example.cron.entity.Job;
import com.example.cron.util.JobUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	JobDao jobDao;

	@Autowired
	JobUtil jobUtil;

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof ContextRefreshedEvent) {
			try {
				List<Job> list = jobDao.findAll();
				for (Job job : list) {
					if (job.getState() == 1) {
						String jobName = job.getJobName();
						String groupName = job.getGroupName();
						Class<? extends org.quartz.Job> clazz = (Class<? extends org.quartz.Job>) Class.forName(job.getClazz());
						String exp = job.getExp();
						jobUtil.addJob(jobName, groupName, clazz, exp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
