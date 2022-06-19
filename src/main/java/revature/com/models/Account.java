package revature.com.models;

import java.math.BigInteger;
import java.util.Random;

public class Account {

	public static int GLOBAL_ACOUNT_NUMBER = 1;
	private int accountNumber;
	private double balance=0;
	
	public Account() {
		this.accountNumber = accountNumber;
		this.balance = balance;
		setAccountNumber();
	}
	
	public Account(int accountNumber, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		setAccountNumber();
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber() {
		BigInteger b = new BigInteger(256, new Random());
		int GLOBAL_ACOUNT_NUMBER = 1;
		GLOBAL_ACOUNT_NUMBER+=Math.abs(b.intValue()+((int) (10*Math.random())));		
		this.accountNumber = GLOBAL_ACOUNT_NUMBER;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNumber;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
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
		if (accountNumber != other.accountNumber)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		return true;
	}
	
	
	
}
