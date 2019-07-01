package com.example.cron;

import com.example.cron.config.SampleJob;
import com.example.cron.dao.JobDao;
import com.example.cron.entity.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CronApplicationTests {

	@Autowired
	JobDao jobDao;

	@Test
	public void contextLoads() {
//		Job job = new Job();
//		job.setClazz(SampleJob.class.getName());
//		job.setExp("*/1 * * * * ?");
//		job.setState(1);
//		job.setRemark("a");
//		jobDao.save(job);
	}

}
