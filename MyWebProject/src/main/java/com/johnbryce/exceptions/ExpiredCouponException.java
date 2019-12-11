package com.johnbryce.exceptions;

public class ExpiredCouponException extends Exception {
	
	public ExpiredCouponException() {
		super("Coupon expired!");
	}

}
