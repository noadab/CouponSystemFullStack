package com.CouponSystemSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.CouponSystemSpring.test.Test;

@SpringBootApplication
@EnableScheduling
public class CouponSystemSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx= SpringApplication.run(CouponSystemSpringApplication.class, args);

		Test test=ctx.getBean(Test.class);
		test.testAll();
//		CustomerService customerService=ctx.getBean(CustomerService.class);
//		customerService.setCustomer(customerService.getCustomerById(2));
//		System.out.println(customerService.getCustomerCoupons().get(0).getCompany());
		ctx.close();



	}

}
