package com.CouponSystemSpring.services;

import java.time.LocalDate;
import java.util.ArrayList;
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
		
		Coupon coupon;
		coupon = couponsRepository.findOneCouponById(couponId);
		
		
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
		
		List<Coupon> coupons = new ArrayList<>();
		try {
			coupons = getCustomerCoupons();
			if (coupons.contains(coupon)) {
				throw new AlreadyExistInDBException("You allready buy this coupon");
			}
		} catch (DoesNotExistInDBException e) {
		}	
		
		List <Customer> customers=customersRepository.findByCoupons(coupon);

		customers.add(customer);
		coupon.setCustomers(customers);
		coupon.setAmount(coupon.getAmount()-1);
		
		couponsRepository.save(coupon);
		
	}

	public List<Coupon> getCustomerCoupons() throws DoesNotExistInDBException {
		List<Coupon> customerCoupons = couponsRepository.findByCustomers(customer);
		if(customerCoupons.isEmpty()) {
			throw new DoesNotExistInDBException("There is no coupons for this customer");
		}
		return couponsRepository.findByCustomers(customer);
		
	}
	
	public List<Coupon> getCustomerCoupons(Category category) throws DoesNotExistInDBException {
		List<Coupon> customerCoupons = couponsRepository.findByCustomersAndCategory(customer, category);
		if(customerCoupons.isEmpty()) {
			throw new DoesNotExistInDBException("There is no coupons in " + category + " category for this customer");
		}
		return customerCoupons;


	}
	
	public List<Coupon> getCustomerCoupons(double maxPrice) throws DoesNotExistInDBException {
		List<Coupon> customerCoupons = couponsRepository.findByCustomersAndPriceLessThan(customer, maxPrice);
		if(customerCoupons.isEmpty()) {
			throw new DoesNotExistInDBException("There is no coupons cheaper than " + maxPrice + " for this customer");
		}
		return customerCoupons;

		
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
