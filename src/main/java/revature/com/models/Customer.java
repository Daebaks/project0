package revature.com.models;

import java.util.ArrayList;
import java.util.List;
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
	private List<Account> acs = new ArrayList<Account>();
	
	
	public Customer(String firstName, String lastname, String phone, String email, String username, String password, List<Account> acs) {
		super();
		this.firstName = firstName;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.acs = acs;
		setCustomerId();
	}
	
	private void setCustomerId() {
		GLOBAL_ID++;
		this.customerId = GLOBAL_ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCustomerId() {
		return customerId;
	}


	public List<Account> getAcs() {
		return acs;
	}

	public void setAcs(List<Account> acs) {
		this.acs = acs;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastname=" + lastname + ", phone=" + phone + ", email=" + email
				+ ", username=" + username + ", password=" + password + ", customerId=" + customerId + ", acs=" + acs
				+ "]";
	}

	 
	
	
}
