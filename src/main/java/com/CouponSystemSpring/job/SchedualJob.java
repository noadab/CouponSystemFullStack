package com.CouponSystemSpring.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SchedualJob {

	// Control activation and pause daily job thread
	private static final String SCHEDULED_TASKS = "scheduledTasks";

	@Autowired
	private ScheduledAnnotationBeanPostProcessor postProcessor;

	@Autowired
	private CouponsExpirationDailyJob scheduledTasks;
	
	public void stopSchedule(){
		if(postProcessor.getScheduledTasks().size()==1) {
			 postProcessor.postProcessBeforeDestruction(scheduledTasks, SCHEDULED_TASKS);
			 System.err.println("SCHEDUAL DAILY JOB STOP RUNNING");
		}
		else {
			System.err.println("SCHEDUAL DAILY JOB DIDN'T RUNNING");
		}
	   
	}
	
	public void startSchedule(){
		if(postProcessor.getScheduledTasks().size()==0) {
			System.err.println("SCHEDUAL DAILY JOB START RUNNING");
			postProcessor.postProcessAfterInitialization(scheduledTasks, SCHEDULED_TASKS);
		}
		else {
			System.err.println("SCHEDUAL DAILY JOB ALLREADY RUNNING");
		}
	}
	
	

}
