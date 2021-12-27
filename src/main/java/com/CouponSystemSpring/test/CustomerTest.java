package com.CouponSystemSpring.test;

import java.util.InputMismatchException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvalidInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.services.ClientType;
import com.CouponSystemSpring.services.CustomerService;


@Controller
public class CustomerTest extends ClientTest {

	// Customer Connection, Menu and function options
	@Autowired
	private CustomerService customerService;

	public CustomerTest() {
		super();
		
	}
	
	private final String CUSTOMER_MENU_STR = "Customer Menu:\n" 
									+ "1. Purchase a coupon\n"
									+ "2. View all your coupons\n" 
									+ "3. View all your coupons in one category\n"
									+ "4. View all your coupons up to max price\n" 
									+ "5. View customer details\n" 
									+ "6. Logout";
	
	@Override
	public void connection() throws LoginException{
		
		String email = scannerManager.getStr("Email:");
		String password = scannerManager.getStr("Password:");

		customerService = (CustomerService) loginManager.login(email, password, ClientType.CUSTOMER);
		if (customerService == null) {
			throw new LoginException();
		} else {
			System.out.println("Login Succeeded\n");
			setUserLogging(true);
			while (isUserLogging()) {
				try {
					menu();
				} catch (InputMismatchException e) {
					System.out.println("You need to enter a number from the list");
					input.next();
				} catch  (InvalidInputException | AlreadyExistInDBException | DoesNotExistInDBException | CanNotPurchaseException e) {
					System.out.println(e.getMessage());
				}  finally {
					System.out.print("\n");
				}
			}
		}

	}

	@Override
	protected void menu() throws InvalidInputException, AlreadyExistInDBException, CanNotPurchaseException, DoesNotExistInDBException {
		int numOption = scannerManager.getInt(CUSTOMER_MENU_STR);
		switch (numOption) {
		case 1:
			PurchaseCoupon();
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
			customerService = null;
			break;
		default:
			throw new InvalidInputException("" + numOption);

		}
	}

	private void viewCustomerDetails() throws DoesNotExistInDBException {
		
			System.out.println("Your Details:\n"+ customerService.getCustomerDetails());
	} 
	private void viewAllCouponsByMaxPrice() {
		double maxPrice = scannerManager.getDouble("Enter coupons maximum price ");
		List <Coupon> coupons= customerService.getCustomerCoupons(maxPrice);
		if (!coupons.isEmpty()) {
			System.out.println("All coupons until "+maxPrice+":\n"+coupons);
		}
		else {
			System.out.println("There is no coupons cheaper than "+maxPrice+" for this customer");
		}
	}
	private void viewAllCouponsByCategory()  {
		Category category = scannerManager.getCategory();
		List <Coupon> coupons= customerService.getCustomerCoupons(category);
		if (!coupons.isEmpty()) {
			System.out.println("All "+category+" coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons in "+category+" category for this customer");
		}
	}
	private void viewAllCoupons() {
		List <Coupon> coupons= customerService.getCustomerCoupons();
		if (!coupons.isEmpty()) {
			System.out.println("All company coupons:\n"+coupons);
		}
		else {
			System.out.println("There is no coupons for this customer");
		}		
	}
	private void PurchaseCoupon() {
		int idCoupon=scannerManager.getInt("Enter the id coupon you want to purchase:");
		try {
			customerService.purchaseCoupon(idCoupon);
			System.out.println("Enjoy your new coupon! ");
		} catch (DoesNotExistInDBException e) {
			System.out.println("This coupon doesn't exits");
		} catch (AlreadyExistInDBException e) {
			System.out.println("You already buy this coupon");
		} catch ( CanNotPurchaseException e) {
			System.out.println(e.getMessage());
		} 
	}
}
