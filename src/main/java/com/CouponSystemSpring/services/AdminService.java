package com.CouponSystemSpring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;

import com.CouponSystemSpring.exceptions.AllreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.UpdateException;

@Service
public class AdminService extends AbstractClientService {
	//Administration business logic
	
	private final String adminEmail = "admin@admin.com";
	private final String adminPassword = "admin";

	public AdminService() {
		super();
	}

	@Override
	public boolean login(String email, String password) {

		if ((email.equals(adminEmail)) && (password.equals(adminPassword))) {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws AllreadyExistInDBException {

		if ((companiesRepository.existsByEmailAndPassword(company.getEmail(), company.getPassword()))) {
			throw new AllreadyExistInDBException();
		}
		if (companiesRepository.existsById(company.getId())) {
			throw new AllreadyExistInDBException();
		}
		if (companiesRepository.existsByEmail(company.getEmail()) || companiesRepository.existsByName(company.getName())) {
			throw new AllreadyExistInDBException();
		}
		
		
		companiesRepository.save(company);
	}

	public void updateCompany(Company company) throws UpdateException, DoesNotExistInDBException {

		if (!(companiesRepository.existsById(company.getId()))) {
			throw new DoesNotExistInDBException("The company doesn't exist");
		}
		
		Company currCompany = companiesRepository.findOneCompanyById(company.getId());

		if (!company.getName().equals(currCompany.getName())) {
			throw new UpdateException("name");
		}
		
		companiesRepository.save(company);

	
	}

	public void deleteCompany(int companyId) throws DoesNotExistInDBException {

		if (!(companiesRepository.existsById(companyId))) {
			throw new DoesNotExistInDBException();
		}

		companiesRepository.deleteById(companyId);
	}

	public List<Company> getAllCompanies() throws DoesNotExistInDBException {

		List <Company> allCompanies=companiesRepository.findAll();
		
		if (allCompanies==null) {
			throw new DoesNotExistInDBException();
		}
		
		for (Company company : allCompanies) {
			List<Coupon> couponsCompany = couponsRepository.findByCompany(company);
			company.setCoupons(couponsCompany );
		}
		
		return allCompanies;
	}

	public Company getOneCompany(int companyId) throws DoesNotExistInDBException {
		
		if (!companiesRepository.existsById(companyId)) {
			throw new DoesNotExistInDBException();
		}
		
		Company currCompany=companiesRepository.findOneCompanyById(companyId);
		List<Coupon> couponsCompany = couponsRepository.findByCompany(currCompany);
		currCompany.setCoupons(couponsCompany);
		return currCompany;
	}

	public void addCustomer(Customer customer) throws AllreadyExistInDBException {
		
		if (customersRepository.existsByEmail(customer.getEmail())) {
			throw new AllreadyExistInDBException();
		}
		if (customersRepository.existsById(customer.getId())) {
			throw new AllreadyExistInDBException();
		}
		
		//??
		for (Coupon coupon : customer.getCoupons()) {
			couponsRepository.save(coupon);
		}
		
		customersRepository.save(customer);
	}

	public void updateCustomer(Customer customer) throws DoesNotExistInDBException {

		if (!customersRepository.existsById(customer.getId())) {
			throw new DoesNotExistInDBException("The customer doesn't exist");
		}
		
		customersRepository.save(customer);
	
	}

	public void deleteCustomer(int customerId) throws DoesNotExistInDBException {
		if (!customersRepository.existsById(customerId)) {
			throw new DoesNotExistInDBException();
		}
		
		Customer customer=customersRepository.findOneCutomerById(customerId);
		
		customersRepository.deleteById(customerId);
		couponsRepository.deleteByCustomers(customer);
	}

	public List <Customer> getAllCustomers() throws DoesNotExistInDBException {
		
		List<Customer> allCustomers=customersRepository.findAll();
		
		if (allCustomers==null) {
			throw new DoesNotExistInDBException();
		}
		
		for (Customer customer : allCustomers) {
			customer.setCoupons(couponsRepository.findByCustomers(customer));
		}
		return allCustomers;
		
	}

	public Customer getOneCustomer(int customerId) throws DoesNotExistInDBException {

		if (!customersRepository.existsById(customerId)) {
			throw new DoesNotExistInDBException();
		}
		
		Customer customer=customersRepository.findOneCutomerById(customerId);
		customer.setCoupons(couponsRepository.findByCustomers(customer));
		return customer;
	}	


}
