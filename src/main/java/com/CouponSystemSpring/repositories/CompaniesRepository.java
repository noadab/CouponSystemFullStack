package com.CouponSystemSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystemSpring.entities.Company;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Integer> {
	
	public default Company findOneCompanyById(int companyId) {
		return findById(companyId).iterator().next();
		
	}
	
	public boolean existsByEmailAndPassword(String email,String password);
	
	public List <Company> findByEmail (String companyEmail) ;
	
	public List <Company> findById (int companyId) ;
	
	public boolean existsByEmail(String email);
	
	public boolean existsByName(String Name);

	
}
