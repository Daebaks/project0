package revature.com.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class Account implements Serializable {

	private int id;						//Unique id# for the account
	private double balance;		//The balance
	private int user_a_id;		    //User's id for this account's holder
	private boolean active;		    //Validity of the account
	
	
	public Account() {
		super();
	}


	public Account(int id, double balance, int user_a_id, boolean active) {
		super();
		this.id = id;
		this.balance = balance;
		this.user_a_id = user_a_id;
		this.active = active;
	}


	public Account(double balance, int user_a_id, boolean active) {
		super();
		this.balance = balance;
		this.user_a_id = user_a_id;
		this.active = active;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public int getUser_a_id() {
		return user_a_id;
	}


	public void setUser_a_id(int user_a_id) {
		this.user_a_id = user_a_id;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", user_a_id=" + user_a_id + ", active=" + active + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(active, balance, id, user_a_id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return active == other.active && Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& id == other.id && user_a_id == other.user_a_id;
	}

	
	
	
	 
}
