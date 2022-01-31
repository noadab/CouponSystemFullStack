package com.CouponSystemSpring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.repositories.CouponsRepository;

@Service
public class UIService {

	@Autowired
	private  CouponsRepository couponsRepository;
	
	public UIService() {
		super();
	}
	
	public List<Coupon> allCoupons () throws DoesNotExistInDBException{
		List<Coupon> allCoupons=couponsRepository.findAll();
		if (allCoupons.isEmpty()) {
			throw new DoesNotExistInDBException("No coupons available");
		}
		return allCoupons;
	}

}
