package com.johnbryce.exceptions;

public class WrongAdminDetailsException extends Exception {
	
	public WrongAdminDetailsException() {
		super("Admin login details are wrongs! please try again.");
	}


}
