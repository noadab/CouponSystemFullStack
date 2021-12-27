package com.CouponSystemSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;



@Repository
public interface CustomersRepository extends JpaRepository <Customer, Integer> {
	
	public default Customer findOneCustomerById(int customerId) {
		return findById(customerId).iterator().next();
		
	}
	
	public void deleteById(int customerId);
	
	public boolean existsByEmailAndPassword(String email,String password);
	
	public boolean existsByEmail(String Email);
	
	public List <Customer> findById (int customerId) ;
	
	public List <Customer> findByEmail (String customerEmail) ;
	
	public List <Customer> findByCoupons (Coupon coupon);

}
