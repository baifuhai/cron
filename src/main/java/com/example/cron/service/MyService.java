package com.example.cron.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

	public void test() {
		System.out.println(System.currentTimeMillis() + "MyService test...");
	}

	public void test2() {
		System.out.println(System.currentTimeMillis() + "MyService test2...");
	}

}
