package com.CouponSystemSpring.controllers;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.helpers.Credentials;
import com.CouponSystemSpring.helpers.SimpleTokenManager;
import com.CouponSystemSpring.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	SimpleTokenManager simpleTokenManager;
	@Autowired
	private CustomerService customerService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials cred) {
		System.out.println("email: " + cred.getEmail() + " password: " + cred.getPassword());
		if (customerService.login(cred.getEmail(), cred.getPassword())) {
			String token = simpleTokenManager.getNewToken();
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Email or password are incorrect, can't log in", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/all/{token}")
	@ResponseBody
	public ResponseEntity<?> viewAllCoupons(@PathVariable String token) {
		System.out.println("Got a request (all) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				List<Coupon> coupons = customerService.getCustomerCoupons();
				return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/allByMaxPrice/{maxPrice}/{token}")
	@ResponseBody
	public ResponseEntity<?> viewAllCouponsByMaxPrice(@PathVariable double maxPrice, @PathVariable String token) {
		System.out.println("maxPrice: " + maxPrice);
		System.out.println("Got a request (all by max price) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			List<Coupon> coupons;
			try {
				coupons = customerService.getCustomerCoupons(maxPrice);
				return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}

		}

		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/allByCategory/{category}/{token}")
	@ResponseBody
	public ResponseEntity<?> viewAllCouponsByCategory(@PathVariable Category category, @PathVariable String token) {
		System.out.println("category: " + category);
		System.out.println("Got a request (all by max price) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				List<Coupon> coupons = customerService.getCustomerCoupons(category);
				return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}

		}

		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/customer/{token}")
	@ResponseBody
	public ResponseEntity<?> viewCompanyDetails(@PathVariable String token) {
		System.out.println("Got a request details");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				Customer customer = customerService.getCustomer();
				System.out.println(customer);
				return new ResponseEntity<Customer>(customer, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("Can't show customer details", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/purchasesCoupon")
	public ResponseEntity<?> purchasesCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String token) {
		System.out.println("Got a call to purchase "+coupon);
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				customerService.purchaseCoupon(coupon.getId());
				return new ResponseEntity<String>("Enjoy your new coupon! ", HttpStatus.OK);
			} catch (AlreadyExistInDBException | CanNotPurchaseException | DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);

	}
}