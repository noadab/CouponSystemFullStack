package com.CouponSystemSpring.test;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.CouponSystemSpring.exceptions.AlreadyExistInDBException;
import com.CouponSystemSpring.exceptions.CanNotPurchaseException;
import com.CouponSystemSpring.exceptions.DoesNotExistInDBException;
import com.CouponSystemSpring.exceptions.InvalidInputException;
import com.CouponSystemSpring.exceptions.LoginException;
import com.CouponSystemSpring.exceptions.UpdateException;
import com.CouponSystemSpring.job.ScheduledDailyJob;


@Controller
public class Test {

	private Scanner input=new Scanner(System.in);

	@Autowired
	private ScheduledDailyJob scheduledDailyJob;

	private boolean connectProgram = true;
	private int numOption;

	@Autowired
	private AdminTest adminTest;
	@Autowired
	private CompanyTest companyTest;
	@Autowired
	private CustomerTest customerTest;

	private final String MAIN_MENU_STR = "choose from main num of operation:\n"
										 + "1. Connection as Admin\n"
										 + "2. Connection as Company\n" 
										 + "3. Connection as Customer\n"
										 + "4. Start daily job\n"
										 + "5. Stop daily job\n" 
										 + "6. Exit";
	
	
	public void testAll()  {
	
		while (connectProgram) {
			try {
				
				mainMenu();
			} catch(InputMismatchException e) {
				System.out.println("You need to enter a number from the list\n");
				input.next();
			} catch (LoginException | InvalidInputException | AlreadyExistInDBException | DoesNotExistInDBException | UpdateException |CanNotPurchaseException e) {
				System.out.println(e.getMessage()+"\n");
			} catch (SQLException   e) {
				System.out.println("SQL exception\n");
			} catch (InterruptedException  e) {
				System.out.println("Interrupted exception\n");
			} 
		}
	}

	private void mainMenu()
			throws SQLException, InterruptedException, LoginException, InputMismatchException, InvalidInputException, AlreadyExistInDBException, DoesNotExistInDBException, UpdateException, CanNotPurchaseException {

		
		System.out.println(MAIN_MENU_STR);
		numOption = input.nextInt();
		switch (numOption) {
		case 1:
			adminTest.connection();
			break;
		case 2:
			companyTest.connection();
			break;
		case 3:
			customerTest.connection();
			break;
		case 4:
			startDailyJob();
			break;
		case 5:
			stopDailyJob();
			break;
		case 6:
			exit();
			break;
		default:
			throw new InvalidInputException("" + numOption);

		}
	

	}

	private void exit(){
		connectProgram = false;
		System.out.println("\nTHE PROGRAM STOP RUNNING...");	
	}

	private void startDailyJob() {
		scheduledDailyJob.startSchedule();
		
		
	}
	

	private void stopDailyJob() {
		scheduledDailyJob.stopSchedule();
	}


	public void setConnectProgram(boolean connectProgram) {
		this.connectProgram = connectProgram;
	}

}
