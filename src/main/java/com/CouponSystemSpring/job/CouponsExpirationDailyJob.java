package com.CouponSystemSpring.job;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.repositories.CouponsRepository;

@Component
public class CouponsExpirationDailyJob {

	//Functionality of the daily job
	
	@Autowired
	private CouponsRepository couponsRepository;
	
	
	@Scheduled(fixedRate=(1000*60*60*24), initialDelay = 1000) //  millis(1000)*seconds(60)*minutes(60)*hours(24)*days=one day
	public void job() {
			
		System.err.println("Daily job start");
		List<Coupon> coupons=couponsRepository.findByEndDateBefore(LocalDate.now());
		coupons.forEach(c->{c.setCustomers(null);});
		couponsRepository.deleteAll(coupons);
		System.err.println("Daily job done.");
		
	}
	
	
	
		
}

