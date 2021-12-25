package com.CouponSystemSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class LoginManager {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;
	
	public LoginManager() {
		super();
	}

	public AbstractClientService login (String email,String password,ClientType clientType) {
		AbstractClientService clientService = null;
		
		switch (clientType) {
		case ADMINISTOR:
			clientService=adminService;
			break;
		case COMPANY:
			clientService=companyService;
			break;
		case CUSTOMER:
			clientService=customerService;
			break;
		}
		
		if (clientService.login(email, password)) {
			return clientService;
		}
		else {
			return null;
		}
		
	}
}
