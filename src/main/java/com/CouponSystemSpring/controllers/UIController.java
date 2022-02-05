package com.CouponSystemSpring.controllers;

import java.util.List;

import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.helpers.SimpleTokenManager;
import com.CouponSystemSpring.helpers.SimpleTokenManager.Token;
import com.CouponSystemSpring.services.UIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UIController {

	@Autowired
	SimpleTokenManager simpleTokenManager;
	@Autowired
	private UIService UIService;

	@GetMapping("/all")
	@ResponseBody
	public ResponseEntity<?> viewAllCoupons() {
		System.out.println("Got a request (all) from web!");
		try {
			List<Coupon> coupons = UIService.allCoupons();
			return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
		} catch (DoesNotExistInDBException e) {
			return new ResponseEntity<String>("There is no coupon", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
		}
	}
<
