package com.CouponSystemSpring.test;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.AllreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvaildInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.services.ClientType;
import com.CouponSystemSpring.services.CustomerService;


@Controller
public class CustomerTest extends ClientTest {

	// Customer Connection, Menu and function options
	@Autowired
	private CustomerService customerFacade;

	public CustomerTest() {
		super();
		
	}
	
	private final String CUSTOMER_MENU_STR = "Customer Menu:\n" 
									+ "1. Purches a coupon\n"
									+ "2. View all your coupons\n" 
									+ "3. View all your coupons in one category\n"
									+ "4. View all your coupons up to max price\n" 
									+ "5. View customer details\n" 
									+ "6. Logout";
	
	@Override
	public void connection() throws LoginException{
		
		String email = scannerManager.getStr("Email:");
		String password = scannerManager.getStr("Password:");

		customerFacade = (CustomerService) loginManager.login(email, password, ClientType.CUSTOMER);
		if (customerFacade == null) {
			throw new LoginException();
		} else {
			System.out.println("Loggin Succeeded\n");
			setUserLogging(true);
			while (isUserLogging()) {
				try {
					menu();
				} catch (InputMismatchException e) {
					System.out.println("You need to enter a number from the list");
					input.next();
				} catch  (InvaildInputException | AllreadyExistInDBException | DoesNotExistInDBException | CanNotPurchaseException e) {
					System.out.println(e.getMessage());
				}  finally {
					System.out.print("\n");
				}
			}
		}

	}

	@Override
	protected void menu() throws InvaildInputException, AllreadyExistInDBException, CanNotPurchaseException, DoesNotExistInDBException {
		int numOption = scannerManager.getInt(CUSTOMER_MENU_STR);
		switch (numOption) {
		case 1:
			purchesCoupon();
			break;
		case 2:
			viewAllCoupons();
			break;
		case 3:
			viewAllCouponsByCategory();
			break;
		case 4:
			viewAllCouponsByMaxPrice();
			break;
		case 5:
			viewCustomerDetails();
			break;
		case 6:
			userLogging = false;
			customerFacade = null;
			break;
		default:
			throw new InvaildInputException("" + numOption);

		}
	}

	private void viewCustomerDetails() throws DoesNotExistInDBException {
		
			System.out.println("Your Details:\n"+customerFacade.getCustomerDetails());
	} 
	private void viewAllCouponsByMaxPrice() {
		double maxPrice = scannerManager.getDouble("Enter coupons maximum price ");
		List <Coupon> coupons=customerFacade.getCustomerCoupons(maxPrice);
		if (!coupons.isEmpty()) {
			System.out.println("All coupons until "+maxPrice+":\n"+coupons);
		}
		else {
			System.out.println("There is no coupons cheaper than "+maxPrice+" for this customer");
		}
	}
	private void viewAllCouponsByCategory()  {
		Category category = scannerManager.getCategory();
		List <Coupon> coupons=customerFacade.getCustomerCoupons(category);
		if (!coupons.isEmpty()) {
			System.out.println("All "+category+" coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons in "+category+" category for this customer");
		}
	}
	private void viewAllCoupons() {
		List <Coupon> coupons=customerFacade.getCustomerCoupons();
		if (!coupons.isEmpty()) {
			System.out.println("All company coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons for this customer");
		}		
	}
	private void purchesCoupon() {
		int idCoupon=scannerManager.getInt("Enter the id coupon you want to purchase:");
		try {
			customerFacade.purchaseCoupon(idCoupon);
			System.out.println("Enjoy your new coupon! ");
		} catch (DoesNotExistInDBException e) {
			System.out.println("This coupon doesn't exsits");
		} catch (AllreadyExistInDBException e) {
			System.out.println("You allready buy this coupon");
		} catch ( CanNotPurchaseException e) {
			System.out.println(e.getMessage());
		} 
	}
}
