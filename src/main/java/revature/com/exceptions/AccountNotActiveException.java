package revature.com.exceptions;

	public class AccountNotActiveException extends RuntimeException{
		
		public AccountNotActiveException(String s) {
			super(s);
		}
}
