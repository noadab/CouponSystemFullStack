package com.CouponSystemSpring.test;

import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvalidInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.services.AbstractClientService;
import com.CouponSystemSpring.services.LoginManager;



@Controller
public abstract class ClientTest {
	
	@Autowired
	protected LoginManager loginManager;
	protected AbstractClientService clientService;
	protected ScannerManager scannerManager=new ScannerManager();
	protected boolean userLogging=false;
	
	protected Scanner input=new Scanner(System.in);
	
	public boolean isUserLogging() {
		return userLogging;
	}

	public void setUserLogging(boolean userLogging) {
		this.userLogging = userLogging;
	}
	
	public abstract void connection() throws SQLException, InterruptedException, LoginException, InvalidInputException, AlreadyExistInDBException, UpdateException, CanNotPurchaseException, DoesNotExistInDBException;
	
	protected abstract void menu() throws InterruptedException, SQLException, AlreadyExistInDBException, DoesNotExistInDBException, InvalidInputException, UpdateException, CanNotPurchaseException;

}
