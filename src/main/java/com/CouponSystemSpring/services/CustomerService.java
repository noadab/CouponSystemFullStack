package com.CouponSystemSpring.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;



@Service
public class CustomerService extends AbstractClientService{
	//Customer client business logic
	
	
	//Hold the login customer
	private Customer customer;
	
	public CustomerService() {
		super();
	}

	@Override
	public boolean login(String email, String Password) {
		if (customersRepository.existsByEmailAndPassword(email, Password)) {
			customer=customersRepository.findByEmail(email).iterator().next();
			return true;
		}
		
		return false;
	}
	
	public void purchaseCoupon(int couponId) throws AlreadyExistInDBException, CanNotPurchaseException, DoesNotExistInDBException {
		
		Coupon coupon=couponsRepository.findOneCouponById(couponId);
		
		if (coupon.equals(null)) {
			throw new DoesNotExistInDBException("Coupon with id: "+couponId+" doesn't exist");
		}
		
		if (coupon.getPrice()==0) {
			throw new CanNotPurchaseException("Can't purchase, the coupon price is 0.");
		}
		
		if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CanNotPurchaseException("Can't purchase, the coupon is expired");
		}
		if (coupon.getAmount()==0) {
			throw new CanNotPurchaseException("The coupon sold out.....sorry");
		}
		
		List<Coupon> coupons=getCustomerCoupons();
		
		if (coupons.contains(coupon)) {
			throw new AlreadyExistInDBException("You allready buy this coupon");
		}
		
		coupons.add(coupon);
		customer.setCoupons(coupons);
		
		List <Customer> customers=customersRepository.findByCoupons(coupon);
		customers.add(customer);
		coupon.setCustomers(customers);
		coupon.setAmount(coupon.getAmount()-1);
		
		couponsRepository.save(coupon);
		customersRepository.save(customer);
	}

	public List<Coupon> getCustomerCoupons() {

		return couponsRepository.findByCustomers(customer);
		
	}
	
	public List<Coupon> getCustomerCoupons(Category category) {
		
		return couponsRepository.findByCustomersAndCategory(customer, category);

	}
	
	public List<Coupon> getCustomerCoupons(double maxPrice) {
	
		return couponsRepository.findByCustomersAndPriceLessThan(customer,maxPrice);

		
	}
	
	public Customer getCustomerDetails() throws DoesNotExistInDBException {
		customer.setCoupons(getCustomerCoupons());
		return customer;
	}

	public Customer getCustomerById(int id) {
		return customersRepository.findOneCustomerById(id);
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
	
}
