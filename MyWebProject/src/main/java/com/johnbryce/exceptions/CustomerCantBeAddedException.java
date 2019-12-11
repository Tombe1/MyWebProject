package com.johnbryce.exceptions;

public class CustomerCantBeAddedException extends Exception {
			
		public CustomerCantBeAddedException() {
			super("You cant add this Customer!");
		}


}
