package com.johnbryce.exceptions;

public class CouponPurchasedException extends Exception {
	
	public CouponPurchasedException() {
		super("Coupon cant be updated purchase!");
	}

}
