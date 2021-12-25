
package com.CouponSystemSpring.test;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.AllreadyExistInDBException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvaildInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.services.ClientType;
import com.CouponSystemSpring.services.CompanyService;


@Controller
public class CompanyTest extends ClientTest  {

	// Company Connection, Menu and function options
	@Autowired
	private static CompanyService companyFacade;

	public CompanyTest() {
		super();
	}
	
	private final static String COMPANY_MENU_STR = "Company Menu:\n"
			 + "1. Add new coupon\n"
			 + "2. Update coupon details\n"
			 + "3. Delete coupon\n"
			 + "4. View all company coupons\n"
			 + "5. View a coupons by category\n"
			 + "6. View all coupons up to max price\n"
			 + "7. View company details\n"
			 + "8. Logout";
	
	

	@Override
	public void connection() throws LoginException{
		
		String email = scannerManager.getStr("Email:");
		String password = scannerManager.getStr("Password:");
		
		companyFacade =(CompanyService)loginManager.login(email, password, ClientType.COMPANY);
		if (companyFacade == null) {
			throw new LoginException();
		} else {
			setUserLogging(true);
			System.out.println("Loggin Succeeded\n");
			while (isUserLogging()) {
				try {
					menu();
				}
				catch (InputMismatchException e) {
					System.out.println("You need to enter a number from the list");
					input.next();
				} catch  (InvaildInputException | AllreadyExistInDBException | DoesNotExistInDBException | UpdateException e) {
					System.out.println(e.getMessage());
				} finally {
					System.out.print("\n");
				}
			}
			
		}
		
	}

	@Override
	protected void menu() throws InvaildInputException, AllreadyExistInDBException, UpdateException, DoesNotExistInDBException {
		int numOption=0;
		try {
			numOption= scannerManager.getInt(COMPANY_MENU_STR);
		} catch (InputMismatchException e) {
			System.out.println("You need to enter a number from the list\n");
		}
		switch(numOption) {
			case 1:
				addCoupon();
				break;
			case 2:
				updateCoupon();
				break;
			case 3:
				deleteCoupon();
				break;
			case 4:
				viewAllCoupons();
				break;
			case 5:
				viewAllCouponsByCategory();
				break;
			case 6:
				viewAllCouponsByMaxPrice();
				break;
			case 7:
				viewCompanyDetails();
				break;
			case 8:
				userLogging=false;
				companyFacade=null;
				break;

			default:
				throw new InvaildInputException("" + numOption);
				
		}
		
		
		
	}
	
	private void viewCompanyDetails() throws DoesNotExistInDBException {
		System.out.println("Your Details:\n"+companyFacade.getCompanyDetails());
	}
	private void viewAllCouponsByMaxPrice() {
		double maxPrice=scannerManager.getDouble("Enter coupons maximum price ");
		List <Coupon> coupons=companyFacade.getCompanyCoupons(maxPrice);
		if (!coupons.isEmpty()) {
			System.out.println("All coupons until "+maxPrice+":\n"+coupons);
		}
		else {
			System.out.println("There is no coupons cheaper than "+maxPrice+" for this company");
		}
		
	}
	private void viewAllCouponsByCategory()  {
		
		Category category = scannerManager.getCategory();
		List <Coupon> coupons=companyFacade.getCompanyCoupons(category);
		if (!coupons.isEmpty()) {
			System.out.println("All "+category+" coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons in "+category+" category for this company");
		}
		
	}
	private void viewAllCoupons()  {
		List <Coupon> coupons=companyFacade.getCompanyCoupons();
		if (!coupons.isEmpty()) {
			System.out.println("All company coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons for this company");
		}
	}
	private void deleteCoupon()  {
		
		int id=scannerManager.getInt("Enter Coupon Id:");
		try{
			companyFacade.deleteCoupon(id);
			System.out.println("The coupon with id: "+id+ " was delete");
		} catch (DoesNotExistInDBException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void updateCoupon() throws DoesNotExistInDBException{
		
		int idCoupon=scannerManager.getInt("Enter the coupon Id you want to update:");
		Coupon currCoupon = companyFacade.getOneCoupon(idCoupon);
		boolean isUpdated=false;
		
		while (!isUpdated) {
		int numOption=scannerManager.getInt("Enter what details you want to update:\n"
											+ "1. Coupon category\n"
											+ "2. Coupon title\n "
											+ "3. Coupon Description\n"
											+ "4. Coupon available date\n"
											+ "5. Coupon expired date\n"
											+ "6. Amount of coupons\n"
											+ "7. Coupon price\n"
											+ "8. Image discription\n"
											+ "9. Exit");
			try {
				switch (numOption) {
				case 1:
					Category category=scannerManager.getCategory();
					currCoupon.setCategory(category);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Category updated");
					break;
				case 2:
					String title=scannerManager.getStr("Enter title");
					currCoupon.setTitle(title);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Description updated");
					break;
				case 3:
					String description=scannerManager.getStr("Enter description");
					currCoupon.setDescription(description);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Description updated");
					break;
				case 4:
					LocalDate startDate=scannerManager.getDate("Enter the date that coupon is available.");
					currCoupon.setStartDate(startDate);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Available coupon date updated");
					break;
				case 5:
					LocalDate endDate=scannerManager.getDate("Enter the date that coupon is expired. ");
					currCoupon.setStartDate(endDate);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Expired coupon date updated");
					break;
				case 6:
					int amount=scannerManager.getInt("Enter amount of coupons that is availbale");
					currCoupon.setAmount(amount);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Amount of coupons updated");
					break;
				case 7:
					double price=scannerManager.getDouble("Enter coupon price:");
					currCoupon.setPrice(price);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Coupon price updated");
					break;
				case 8:
					String image=scannerManager.getStr("Enter image discription:");
					currCoupon.setImage(image);
					companyFacade.updateCoupon(currCoupon);
					isUpdated=true;
					System.out.println("Image discription updated");
					break;
				case 9:
					isUpdated=true;
					break;
				default:
					throw new InvaildInputException("" + numOption);
				}
				isUpdated=true;
			} catch (InvaildInputException | DoesNotExistInDBException| UpdateException e) {
				System.out.println(e.getMessage());
			}
		}

	}
	private void addCoupon() {
		System.out.println("Enter new coupon details:");
	
		try {
			Coupon coupon= scannerManager.getCouponDetails();
			companyFacade.addCoupon(coupon);
			System.out.println("The coupon add to system");
		} catch (AllreadyExistInDBException | InvaildInputException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
