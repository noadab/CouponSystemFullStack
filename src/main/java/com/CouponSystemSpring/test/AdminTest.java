package com.CouponSystemSpring.test;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Customer;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvalidInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.services.AdminService;
import com.CouponSystemSpring.services.ClientType;


@Controller
public class AdminTest extends ClientTest{
	
	// Administration Connection, Menu and function options
	
	@Autowired
	AdminService adminService;
	
	public AdminTest() {
		super();
	}


	private final static String ADMIN_MENU_STR = "Admin Menu:\n"
			   + "1. Add new company\n"
			   + "2. Update company details\n"
			   + "3. Delete company\n"
			   + "4. View all the Companies\n"
			   + "5. View a company by his ID\n"
			   + "6. Add a new customer\n"
			   + "7. Update customer details\n"
			   + "8. Delete customer\n"
			   + "9. View all the customers\n"
			   + "10. View a customer by his ID\n"
			   + "11. Logout";
	

	@Override
	public void connection() throws LoginException {
		String email = scannerManager.getStr("Email:");
		String password = scannerManager.getStr("Password:");
		
		adminService =(AdminService) loginManager.login(email, password, ClientType.ADMINISTRATOR);
		if (adminService ==null) {
			throw new LoginException();
		} else {
			setUserLogging(true);
			System.out.println("Login Succeeded\n");
			while (isUserLogging()) {
				try {
					menu();
				} catch (InputMismatchException e) {
					System.out.println("You need to enter a number from the list");
					input.next();
				} catch (DoesNotExistInDBException | InvalidInputException e) {
					System.out.println(e.getMessage());
				} finally {
					System.out.print("\n");
				}
			}
		}
	}
	
	@Override
	protected void menu() throws DoesNotExistInDBException, InvalidInputException {
		int numOption=0;
		try {
			numOption= scannerManager.getInt(ADMIN_MENU_STR);
		} catch (InputMismatchException e) {
			System.out.println("You need to enter a number from the list\n");
		}
		
		switch(numOption) {
			case 1:
				addCompany();
				break;
			case 2:
				updateCompany();
				break;
			case 3:
				deleteCompany();
				break;
			case 4:
				viewAllCompanies();
				break;
			case 5:
				viewCompanyById();
				break;
			case 6:
				addCustomer();
				break;
			case 7:
				updateCustomer();
				break;
			case 8:
				deleteCustomer();
				break;
			case 9:
				viewAllCustomer();
				break;
			case 10:
				viewCustomerById();
				break;
			case 11:
				setUserLogging(false);
				adminService =null;
				break;
			default:
				throw new InvalidInputException("" + numOption);
				
		}
	}
	
	private void viewCustomerById() {
		int id=scannerManager.getInt("Enter the customer Id");
		try {
			Customer currCustomer = adminService.getOneCustomer(id);
			System.out.println(currCustomer);
		} catch (DoesNotExistInDBException e) {
			System.out.println("This customer doesn't exists.");
		}
		
		
	}
	private void viewAllCustomer() {
		try {
			List<Customer> allCustomers= adminService.getAllCustomers();
			System.out.println(allCustomers);
		} catch (DoesNotExistInDBException e) {
			System.out.println("There is no customers exists in system.");
		}
		
	}
	private void deleteCustomer() {
		
		int id = scannerManager.getInt("Enter customer Id:");
		 
		try{
			adminService.deleteCustomer(id);
			System.out.println("The customer with id: "+id+ " was delete");
		} catch (DoesNotExistInDBException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void updateCustomer() throws DoesNotExistInDBException  {
		
		int idCustomer=scannerManager.getInt("Enter the customer Id you want to update:");
		Customer currCustomer= adminService.getOneCustomer(idCustomer);
		boolean isUpdated=false;
		
		while (!isUpdated) {
		int numOption=scannerManager.getInt("Enter what details you want to update:\n"
												+ "1. First name\n"
												+ "2. Last name\n"
												+ "3. Email\n"
												+ "4. Password\n"
												+ "5. Exit");
			try {
				switch (numOption) {
					case 1:
						String newFirstName=scannerManager.getStr("Enter new first name:");
						currCustomer.setFirstName(newFirstName);
						adminService.updateCustomer(currCustomer);
						isUpdated=true;
						System.out.println("First name update");
						break;
					case 2:
						String newlastName=scannerManager.getStr("Enter new last name:");
						currCustomer.setLastName(newlastName);
						adminService.updateCustomer(currCustomer);
						isUpdated=true;
						System.out.println("Last name update");
						break;
					case 3:
						String newEmail=scannerManager.getStr("Enter new email:");
						currCustomer.setEmail(newEmail);
						adminService.updateCustomer(currCustomer);
						isUpdated=true;
						System.out.println("Email update");
						break;
					case 4:
						String newPassword=scannerManager.getStr("Enter new password:");
						currCustomer.setPassword(newPassword);
						adminService.updateCustomer(currCustomer);
						isUpdated=true;
						System.out.println("Password update");
						break;
					case 5: 
						isUpdated=true;
						break;
					default:
						throw new InvalidInputException("" + numOption);
					}
			} catch ( DoesNotExistInDBException | InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			}

	}
	private void addCustomer() {
		System.out.println("Enter new customer details:");
		Customer newCustomer= scannerManager.getCustomerDetails("customer");
		try {
			adminService.addCustomer(newCustomer);
			System.out.println("The customer added to system");
		} catch (AlreadyExistInDBException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void viewCompanyById() {
		int id=scannerManager.getInt("Enter the company Id");
		try {
			Company currCompany= adminService.getOneCompany(id);
			System.out.println(currCompany);
		} catch (DoesNotExistInDBException e) {
			System.out.println("This company doesn't exists.");
		}
	}
	private void viewAllCompanies(){
		
		try {
			List <Company> allCompanies= adminService.getAllCompanies();
			System.out.println(allCompanies);
		} catch (DoesNotExistInDBException e) {
			System.out.println("There is no companies exists in system.");
		}
		
		
	}
	private void deleteCompany() {
		int id=scannerManager.getInt("Enter company Id");
		try{
			adminService.deleteCompany(id);
			System.out.println("The company with id "+id+ " was delete");
		} catch (DoesNotExistInDBException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void updateCompany() throws DoesNotExistInDBException {

		int idCompany=scannerManager.getInt("Enter the company Id you want to update:");
		Company currCompany= adminService.getOneCompany(idCompany);
		boolean isUpdated=false;
		
		while (!isUpdated) {
			int numOption=scannerManager.getInt("Enter what details you want to update:\n"
												+ "1. Email\n"
												+ "2. Password\n"
												+ "3. exit");
			try {
				switch (numOption) {
				case 1:
					String newEmail=scannerManager.getStr("Enter new email:");
					currCompany.setEmail(newEmail);
					adminService.updateCompany(currCompany);
					isUpdated=true;
					System.out.println("Email update");
					break;
				case 2:
					String newPassword=scannerManager.getStr("Enter new password:");
					currCompany.setPassword(newPassword);
					adminService.updateCompany(currCompany);
					isUpdated=true;
					System.out.println("Password update");
					break;
				case 3:
					isUpdated=true;
					break;
				default:
					throw new InvalidInputException("" + numOption);
				}
			} catch ( UpdateException | DoesNotExistInDBException | InvalidInputException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
	}
	private void addCompany() {
		System.out.println("Enter new company details:");
		Company newCompany= scannerManager.getCompanyDetails("company");
		try {
			adminService.addCompany(newCompany);
			System.out.println("The company added to system");
		} catch (AlreadyExistInDBException e) {
			System.out.println(e.getMessage());
		}
	}

	


}
