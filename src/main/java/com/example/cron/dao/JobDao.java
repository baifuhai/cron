package com.example.cron.dao;

import com.example.cron.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDao extends JpaRepository<Job, Integer> {
}
