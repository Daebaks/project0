package revature.com.models;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

	private int id;						//Unique id# for the account
	private double balance;		//The balance
	private int users_a_id;		    //User's id for this account's holder
	private boolean active;		    //Validity of the account
	
	
	public Account() {
		super();
	}


	public Account(int id, double balance, int users_a_id, boolean active) {
		super();
		this.id = id;
		this.balance = balance;
		this.users_a_id = users_a_id;
		this.active = active;
	}


	public Account(double balance, int users_a_id, boolean active) {
		super();
		this.balance = balance;
		this.users_a_id = users_a_id;
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


	public int getUsers_a_id() {
		return users_a_id;
	}


	public void setUsers_a_id(int users_a_id) {
		this.users_a_id = users_a_id;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", users_a_id=" + users_a_id + ", active=" + active + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(active, balance, id, users_a_id);
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
				&& id == other.id && users_a_id == other.users_a_id;
	}

	
	
	
	 
}
