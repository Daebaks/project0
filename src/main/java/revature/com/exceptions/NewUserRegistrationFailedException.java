package revature.com.exceptions;

public class NewUserRegistrationFailedException extends RuntimeException{
	public NewUserRegistrationFailedException(String s) {
		super(s);
	}
}
