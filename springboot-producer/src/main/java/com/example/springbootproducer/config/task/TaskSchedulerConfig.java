package com.example.springbootproducer.config.task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时器配置
 * @author YE
 *
 */
@Configuration
@EnableScheduling //启动定时任务
public class TaskSchedulerConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		taskRegistrar.setScheduler(taskScheduler());
	}

	/**
	 * 
	 * @return
	 * 添加一个线程池到定时任务中
	 */
	@Bean(destroyMethod="shutdown")
	public Executor taskScheduler() {
		return Executors.newScheduledThreadPool(100);
	}
	
}