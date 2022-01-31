package com.CouponSystemSpring.controllers;


import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.helpers.Credentials;
import com.CouponSystemSpring.helpers.SimpleTokenManager;
import com.CouponSystemSpring.services.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SimpleTokenManager simpleTokenManager;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Credentials cred) {
        System.out.println("email: "+cred.getEmail()+" password: "+cred.getPassword());
        if (adminService.login(cred.getEmail(), cred.getPassword())) {
            String token = simpleTokenManager.getNewToken();
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        return new ResponseEntity<String> ("Email or password are incorrect, can't log in",HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/company/{token}")
	@ResponseBody
	public ResponseEntity <?> viewAllCompanies(@PathVariable String token){
		System.out.println("Got a request (all companies) from client!");
		if (simpleTokenManager.isTokenExist(token)){
			List<Company> companies;
			try {
				companies = adminService.getAllCompanies();
				return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>("There is no companies", HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
			
			
		}
		return new ResponseEntity<>("Session time out! ",HttpStatus.BAD_REQUEST);

	}
    
    @PostMapping("/add/company")
	public ResponseEntity<?> addCompany (@RequestBody Company company, @RequestHeader("token") String token){

		System.out.println("got a call");
		System.out.println("Got a new company: "+company+", token="+token);
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				adminService.addCompany(company);
				return new ResponseEntity<String>("Company added to system", HttpStatus.OK);
			} catch (AlreadyExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<>("Session time out! ",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/update/company")
	public ResponseEntity<?> updateCompany (@RequestBody Company company,  @RequestHeader("token") String token){
		System.out.println("got a call");
		System.out.println("Got a update comapny: "+company+", token="+token);
		if (simpleTokenManager.isTokenExist(token)) {
			
			try {
				adminService.updateCompany(company);
				return new ResponseEntity<String>("Company update", HttpStatus.OK);
			} catch (DoesNotExistInDBException | UpdateException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
			
		}
		return new ResponseEntity<>("Session time out! ",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("delete/company/{id}")
	public ResponseEntity<?> deleteComapny (@PathVariable int id, @RequestHeader("token") String token){
		System.out.println("Got a request (delete) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				adminService.deleteCompany(id);
				return new ResponseEntity<String>("Company delete", HttpStatus.OK);
			} catch (DoesNotExistInDBException  e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<String>("Session time out! ", HttpStatus.BAD_REQUEST);
	}
	
    @GetMapping("/customer/{token}")
	@ResponseBody
	public ResponseEntity <?> viewAllCustomers(@PathVariable String token){
		System.out.println("Got a request (all customers) from client!");
		if (simpleTokenManager.isTokenExist(token)){
			List<Customer> customers;
			try {
				customers = adminService.getAllCustomers();
				return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>("There is no customers", HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Somthing wrong", HttpStatus.METHOD_NOT_ALLOWED);
			}
			
			
		}
		return new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
	}

    @PostMapping("/add/customer")
	public ResponseEntity<?> addCompanyustomer (@RequestBody Customer customer, @RequestHeader("token") String token){

		System.out.println("got a call");
		System.out.println("Got a new company: "+customer+", token="+token);
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				adminService.addCustomer(customer);
				return new ResponseEntity<String>("Customer added to system", HttpStatus.OK);
			} catch (AlreadyExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<>("Session time out! ",HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/update/customer")
	public ResponseEntity<?> updateCompanyustomer (@RequestBody Customer customer,  @RequestHeader("token") String token){
		System.out.println("got a call");
		System.out.println("Got a update comapny: "+customer+", token="+token);
		if (simpleTokenManager.isTokenExist(token)) {
			
			try {
				adminService.updateCustomer(customer);
				return new ResponseEntity<String>("Customer update", HttpStatus.OK);
			} catch (DoesNotExistInDBException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
			
		}
		return new ResponseEntity<>("Session time out! ",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("delete/customer/{id}")
	public ResponseEntity<?> deleteCustomer (@PathVariable int id, @RequestHeader("token") String token){
		System.out.println("Got a request (delete) from client!");
		if (simpleTokenManager.isTokenExist(token)) {
			try {
				adminService.deleteCustomer(id);
				return new ResponseEntity<String>("Customer delete", HttpStatus.OK);
			} catch (DoesNotExistInDBException  e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something went wrong!", HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
		return new ResponseEntity<String>("Session time out! ", HttpStatus.BAD_REQUEST);
	}
	
}
