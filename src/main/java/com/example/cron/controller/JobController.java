package com.example.cron.controller;

import com.example.cron.util.JobUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("job")
public class JobController {

	@Autowired
	JobUtil jobUtil;

	@PostMapping("pause")
	public String pauseJob(String jobName, String groupName) throws SchedulerException {
		jobUtil.pauseJob(jobName, groupName);
		return "success";
	}

	@PostMapping("resume")
	public String resumeJob(String jobName, String groupName) throws SchedulerException {
		jobUtil.resumeJob(jobName, groupName);
		return "success";
	}

	@PostMapping("delete")
	public String deleteJob(String jobName, String groupName) throws SchedulerException {
		jobUtil.deleteJob(jobName, groupName);
		return "success";
	}

	@PostMapping("update")
	public String updateJob(String jobName, String groupName, String cronExpression) throws SchedulerException {
		jobUtil.updateJob(jobName, groupName, cronExpression);
		return "success";
	}

}
