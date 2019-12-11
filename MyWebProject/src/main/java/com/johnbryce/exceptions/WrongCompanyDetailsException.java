package com.johnbryce.exceptions;

public class WrongCompanyDetailsException extends Exception {
	
	public WrongCompanyDetailsException() {
		super("Company login details are wrongs! please try again.");
	}


}
