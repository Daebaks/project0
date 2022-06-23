package revature.com;

import java.util.Scanner;

import revature.com.models.Role;
import revature.com.models.User;
import revature.com.service.AccountService;
import revature.com.service.UserService;

public class BankingApp {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		run();
		
	}
	

	public static void run() {
		
		     // registering template
			AccountService as = new AccountService();
			as.viewAllAccounts();
//		System.out.println("Enter username");
//		String username = scan.next();
//		
//		System.out.println("Enter password");
//		String password = scan.next();
//		
//		User u = new User(username, password, Role.Customer, null);
//		UserService us = new UserService();
//		us.register(u);
		
		
	}

}
