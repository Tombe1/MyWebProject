package com.johnbryce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWebProjectApplication.class, args);
	}
	
	//http://localhost:8080/swagger-ui.html
	
	//Admin tests:
	//post - addCompany = working.
	//get - getAllCompanies = working.
	//get - getOneCompany = working.
	//put - updateCompany = ?.
	//delete - deleteCompany = working.
	//post - addCustomer = working.
	//get - getAllCustomers = working.
	//get - getOneCustomer = working.
	//delete - deleteCustomer = working.
	//put - updateCustomer = ?.
	
	//Companies tests:
	//get - getCompanyDetails = working.
	//get - getCompanyCoupons = working.
	//get - getCompanyCouponsByCategory = working.
	//get - getCompanyCouponsByMaxPrice = working.
	//post - addCoupon = working.
	//put - updateCoupon = ?.
	//delete - deleteCoupon = working.
	
	//Customers tests:
	//get - getCompanyDetails = working.
	//get - getCustomerCoupons = working.
	//get - getCustomerCouponsByCategory = working.
	//get - getCustomerCouponsByMaxPrice = working.
	//post - addPurchaseCoupon = working.
	//delete - deletePurchaseCoupon = working. (deleting the entire Coupon exist)

}
