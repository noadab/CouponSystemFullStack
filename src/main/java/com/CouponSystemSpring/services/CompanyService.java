package com.CouponSystemSpring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.UpdateException;

@Service
public class CompanyService extends AbstractClientService {
	//Company client business logic
	
	
	//Hold the login company
	private Company company;

	public CompanyService() {
		super();
	}

	@Override
	public boolean login(String email, String Password) {
		if (companiesRepository.existsByEmailAndPassword(email, Password)) {
			company = companiesRepository.findByEmail(email).iterator().next();
			return true;
		}
		return false;
	}

	public void addCoupon(Coupon coupon) throws AlreadyExistInDBException {

		if (couponsRepository.existsByTitle(coupon.getTitle())) {
			throw new AlreadyExistInDBException("This coupon already exists");
		}

		coupon.setCompany(company);
		couponsRepository.save(coupon);

	}

	public void updateCoupon(Coupon coupon) throws DoesNotExistInDBException, UpdateException {

		if (!(couponsRepository.existsById(coupon.getId()))) {
			throw new DoesNotExistInDBException("The coupon doesn't exist, can't update");
		}

		Coupon currCoupon = couponsRepository.findOneCouponById(coupon.getId());

		if (!coupon.getTitle().equals(currCoupon.getTitle())) {
			throw new UpdateException("title");
		}

		couponsRepository.save(coupon);

	}

	public void deleteCoupon(int couponId) throws DoesNotExistInDBException {

		List<Coupon> companyCoupons = couponsRepository.findByCompany(company);

		if (!companyCoupons.contains(couponsRepository.findOneCouponById(couponId))) {
			throw new DoesNotExistInDBException("The coupon doesn't exist");
		}

		Coupon coupon = couponsRepository.findOneCouponById(couponId);
		companyCoupons.remove(coupon);
		company.setCoupons(companyCoupons);
		coupon.setCustomers(null);

		companiesRepository.save(company);
		couponsRepository.deleteById(couponId);
	}

	public Coupon getOneCoupon(int couponId) throws DoesNotExistInDBException {

		if (!couponsRepository.existsById(couponId)) {
			throw new DoesNotExistInDBException("The coupon doesn't exist");
		}

		Coupon currCoupon = couponsRepository.findOneCouponById(couponId);

		List<Coupon> couponsCompany = getCompanyCoupons();
		if (!couponsCompany.contains(currCoupon)) {
			throw new DoesNotExistInDBException("This coupon doesn't found in your coupons");
		}

		return currCoupon;
	}


	public List<Coupon> getCompanyCoupons() {

		List<Coupon> companyCoupons = couponsRepository.findByCompany(company);
		return companyCoupons;

	}

	public List<Coupon> getCompanyCoupons(Category category) {

		return couponsRepository.findByCompanyAndCategory(company, category);

	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {

		return couponsRepository.findByCompanyAndPriceLessThan(company, maxPrice);

	}

	public Company getCompanyDetails() throws DoesNotExistInDBException {

		company.setCoupons(getCompanyCoupons());
		return company;
	}

	public Company getCompanyById(int companyId) {

		return companiesRepository.findOneCompanyById(companyId);
	}

}
