package com.CouponSystemSpring.controllers;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.helpers.Credentials;
import com.CouponSystemSpring.helpers.SimpleTokenManager;
import com.CouponSystemSpring.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private SimpleTokenManager simpleTokenManager;
	@Autowired
	private CompanyService companyService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Credentials cred) {
		System.out.println("email: " + cred.getEmail() + " password: " + cred.getPassword());
		if (companyService.login(cred.getEmail(), cred.getPassword())) {
			String token = simpleTokenManager.getNewToken();
			simpleTokenManager.initThread();
			return new ResponseEntity<String>(token, HttpStatus.OK);

		}
		return new ResponseEntity<String>("Email or password are incorrect, can't log in", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String token) {

		System.out.println("got a call");
		System.out.println("Got a new coupon: " + coupon + ", token=" + token);
		Coupon currCoupon = coupon;
		currCoupon.setCompany(companyService.getCompany());//
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				companyService.addCoupon(currCoupon);
				return new ResponseEntity<String>("Coupon added to system", HttpStatus.OK);
			} catch (AlreadyExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<String>("Session time out! ", HttpStatus.BAD_REQUEST);

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader("token") String token) {
		System.out.println("got a call");
		System.out.println("Got a update coupon: " + coupon + ", token=" + token);
		if (simpleTokenManager.isTokenExist(token)) {

			try {
				companyService.updateCoupon(coupon);
				return new ResponseEntity<String>("Coupon update", HttpStatus.OK);
			} catch (DoesNotExistInDBException | UpdateException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}

		}
		return new ResponseEntity<String>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCoupon(@PathVariable int id, @RequestHeader("token") String token) {
		System.out.println("Got a request (delete) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				companyService.deleteCoupon(id);
				return new ResponseEntity<String>("Coupon delete", HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<String>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/all/{token}")
	@ResponseBody
	public ResponseEntity<?> viewAllCoupons(@PathVariable String token) {
		System.out.println("Got a request (all) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				List<Coupon> coupons = companyService.getCompanyCoupons();
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

			try {
				List<Coupon> coupons = companyService.getCompanyCoupons(maxPrice);
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
				List<Coupon> coupons = companyService.getCompanyCoupons(category);
				return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}

		}

		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/company/{token}")
	@ResponseBody
	public ResponseEntity<?> viewCompanyDetails(@PathVariable String token) {
		System.out.println("Got a request details");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				Company company = companyService.getCompany();
				System.out.println(company);
				return new ResponseEntity<Company>(company, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("Can't show company details", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>("Session time out! ", HttpStatus.BAD_REQUEST);
	}

}
