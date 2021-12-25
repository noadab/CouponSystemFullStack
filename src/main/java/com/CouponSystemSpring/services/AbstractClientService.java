package com.CouponSystemSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystemSpring.repositories.CompaniesRepository;
import com.CouponSystemSpring.repositories.CouponsRepository;
import com.CouponSystemSpring.repositories.CustomersRepository;

@Service
public abstract class AbstractClientService {
	
	@Autowired
	protected CompaniesRepository companiesRepository;
	@Autowired
	protected CouponsRepository couponsRepository;
	@Autowired
	protected CustomersRepository customersRepository;
	
	public abstract boolean login (String email,String Password) ;
	
	
}
