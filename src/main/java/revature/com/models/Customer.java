package revature.com.models;

import java.util.Scanner;

public class Customer {

	private static Scanner scan = new Scanner(System.in);
	public static int GLOBAL_ID = 100;
	
	
	private String firstName;
	private String lastname;
	private String phone;
	private String email;
	private String username;
	private String password;
	private int customerId;
	
	
	public Customer(String firstName, String lastname, String phone, String email, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	private void setCustomerId() {
		GLOBAL_ID++;
		this.customerId = GLOBAL_ID;
	}
	
	
	
	
	
	
	
}
