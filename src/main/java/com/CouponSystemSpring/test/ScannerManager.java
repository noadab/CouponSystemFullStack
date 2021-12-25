package com.CouponSystemSpring.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.entities.Category;
import com.CouponSystemSpring.entities.Company;
import com.CouponSystemSpring.entities.Coupon;
import com.CouponSystemSpring.entities.Customer;
import com.CouponSystemSpring.exceptions.InvaildInputException;



@Controller
public class ScannerManager {

	// Get the input from the client and check if is valid 
	private Scanner input ;

	final private int maxYear=2050;
	final private int minYear=2000;
	final private int maxMonth=12;
	final private int minMonth=1;
	final private int maxDay=31;
	final private int minDay=1;
	
	public ScannerManager () {
		input = new Scanner(System.in);
	}
	
	public Customer getCustomerDetails(String massege) {
		String firstName= getStr("Enter "+massege+" first name: ");
		String lastName= getStr("Enter "+massege+" last name: ");
		String email= getStr ("Enter "+massege+" email: ");
		String password= getStr("Enter "+massege+" password: ");
	
		List<Coupon> coupons = new ArrayList<>();
		
		Customer currCustomer= new Customer();
		currCustomer.setFirstName(firstName);
		currCustomer.setLastName(lastName);
		currCustomer.setEmail(email);
		currCustomer.setPassword(password);
		currCustomer.setCoupons(coupons);
		
		return currCustomer;
	
	}
	
	public Company getCompanyDetails(String massege) throws InputMismatchException{
		String name=getStr("Enter "+massege+" Name:");
		String email=getStr("Enter "+massege+" Email:");
		String password=getStr("Enter "+massege+" password:");
		
		Company currCompany= new Company();
		currCompany.setName(name);
		currCompany.setEmail(email);
		currCompany.setPassword(password);

		return currCompany;
		
	}

	public Coupon getCouponDetails() throws InvaildInputException {

		Category category=getCategory();
		String title=getStr("Enter coupon title:");
		String description=getStr("Enter description:");
		LocalDate startDate=getDate("Enter the date that coupon is available.");
		LocalDate endDate=getDate("Enter the date that coupon is expired.");
		int amount=getInt("Enter amount of coupons that is availbale");
		double price=getDouble("Enter coupon price:");
		String image=getStr("Enter image discription:");
			
		Coupon currCoupon=new Coupon();
		currCoupon.setCategory(category);
		currCoupon.setTitle(title);
		currCoupon.setDescription(description);
		currCoupon.setStartDate(startDate);
		currCoupon.setEndDate(endDate);
		currCoupon.setAmount(amount);
		currCoupon.setPrice(price);
		currCoupon.setImage(image);
		
		return currCoupon;
	}
	
	public int getInt(String msg) {
		int num=0;
		while(num<=0) {
			try {
				System.out.println(msg);
				num=input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invaild input, Enter again..");
				input.next();
			} 
		}
		return num;
	}
	
	public double getDouble(String msg) {
		double num=0;
		while(num<=0) {
			try {
				System.out.println(msg);
				num=input.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("Invaild input, Enter again..");
				input.next();
			} 
		}
		return num;
	}
	
	public String getStr(String msg) {
		String Str=null;
		while(Str==null) {
			try {
				System.out.println(msg);
				Str=input.next();
			} catch (InputMismatchException e) {
				System.out.println("Invaild input, Enter again..");
				input.next();
			} 
		}
		return Str;
	}
	
	public Category getCategory() {
		String categorySrt=null;
		while (!Category.isItCategory(categorySrt)){
			try {
				System.out.print("Enter Category:\noptions: ");
				Category.printAllCategory();
				categorySrt=getStr("");
				if (!Category.isItCategory(categorySrt)){
					throw new InvaildInputException(categorySrt);
				}
			} catch (InvaildInputException e) {
				System.out.println(e.getMessage());
			}
		}
		return Category.valueOf(categorySrt);
		
	}
	
	public LocalDate getDate(String msg) {
		LocalDate date=null;
		while (date==null) {
			System.out.println(msg);
			int yearDate=getInt("Year: _ _ _ _"),
					monthDate=getInt("Month: _ _ "),
					dayDate=getInt("Day: _ _ ");
			if ((yearDate>=minYear)   && (yearDate<=maxYear)&&
				(monthDate>=minMonth) && (monthDate<=maxMonth)&&
				(dayDate>=minDay)     && (dayDate<=maxDay)) {
				date=LocalDate.of(yearDate, monthDate, dayDate);
			}
			else {
				System.out.println(yearDate+"-"+monthDate+"-"+dayDate+" is not a real date... Try again");
			}
		}
		return date;
	}
		
}
