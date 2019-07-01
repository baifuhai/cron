package com.example.cron.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class JobUtil {

	@Autowired
//	@Qualifier("Scheduler")
	Scheduler scheduler;

	//创建一个新的任务
	public void addJob(String jobName, String groupName, Class<? extends org.quartz.Job> clazz, String cronExpression) throws SchedulerException {
		JobDetail jobDetail = JobBuilder
				.newJob(clazz)
				.withIdentity(jobName, groupName)
				.build();

		//执行的任务中传入参数
		jobDetail.getJobDataMap().put("sakura", "sakura");

		//创建corn表达式，创建执行任务的时间规范
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		//创建一个触发器，加入上面创建的时间规范
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(jobName, groupName)
				.withSchedule(scheduleBuilder)
				.build();

		//把执行的job任务和创建时间trigger绑定一起
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public void addJob(String jobName, String groupName, Class<? extends org.quartz.Job> clazz, long delay, long interval, int repeatCount, Object... args) throws SchedulerException {
		Date startTime = new Date(System.currentTimeMillis() + delay);
		addJob(jobName, groupName, clazz, startTime, interval, repeatCount, args);
	}

	public void addJob(String jobName, String groupName, Class<? extends org.quartz.Job> clazz, Date startTime, long interval, int repeatCount, Object... args) throws SchedulerException {
		JobDetail jobDetail = JobBuilder
				.newJob(clazz)
				.withIdentity(jobName, groupName)
				.build();

		//执行的任务中传入参数
		jobDetail.getJobDataMap().put("args", args);

		//创建corn表达式，创建执行任务的时间规范
		//CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
				.simpleSchedule()
				.withIntervalInMilliseconds(interval)
				.withRepeatCount(repeatCount);

		//创建一个触发器，加入上面创建的时间规范
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(jobName, groupName)
				.withSchedule(scheduleBuilder)
				.startAt(startTime)
				.build();

		//把执行的job任务和创建时间trigger绑定一起
		scheduler.scheduleJob(jobDetail, trigger);
	}

	//暂停一个任务
	public void pauseJob(String jobName, String groupName) throws SchedulerException {
		scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
	}

	//恢复一个任务
	public void resumeJob(String jobName, String groupName) throws SchedulerException {
		scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
	}

	//删除一个任务
	public void deleteJob(String jobName, String groupName) throws SchedulerException {
		scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
	}

	//修改一个任务
	public void updateJob(String jobName, String groupName, String cronExpression) throws SchedulerException {
		//创建corn表达式，创建执行任务的时间规范
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);

		//获取trigger
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		//把执行的job任务和创建时间trigger绑定一起
		trigger = trigger.getTriggerBuilder()
				.withIdentity(triggerKey)
				.withSchedule(scheduleBuilder)
				.build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	//查询所有的任务
	public String searchJob() throws SchedulerException {
		String groupName = "group_one"; //定义job所在组名称
		Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
		StringBuilder message = new StringBuilder();
		for (JobKey jobKey : jobKeys) {
			message.append(jobKey.getName() + "----------------" + jobKey.getGroup() + "\n");
		}
		return message.toString();
	}

}
