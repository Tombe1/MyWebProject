package com.johnbryce.exceptions;

public class WrongCustomerDetailsException extends Exception {
	
	public WrongCustomerDetailsException() {
		super("Customer login details are wrongs! please try again.");
	}


}
