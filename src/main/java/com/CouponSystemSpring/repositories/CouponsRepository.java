package com.CouponSystemSpring.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;



@Repository
public interface CouponsRepository extends JpaRepository<Coupon, Integer> {

	
	public default Coupon findOneCouponById(int couponId) {
		return findById(couponId).iterator().next();
	}
	
	public void deleteByCustomers(Customer customer);

	public boolean existsByTitle(String title);
	
	public List <Coupon> findById (int couponId) ;
	
	public List <Coupon> findByCompany (Company company);
	
	public List <Coupon> findByCustomers (Customer customer) ;

	public List<Coupon> findByCompanyAndCategory(Company company, Category category);

	public List<Coupon> findByCompanyAndPriceLessThan(Company company, double maxPrice);
	
	public List<Coupon> findByCustomersAndCategory(Customer customer, Category category);

	public List<Coupon> findByCustomersAndPriceLessThan(Customer customer, double maxPrice);

	public List<Coupon> findByEndDateBefore(LocalDate date);
}
