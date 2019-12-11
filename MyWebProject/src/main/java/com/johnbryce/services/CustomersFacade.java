package com.johnbryce.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.johnbryce.beans.Categories;
import com.johnbryce.beans.Coupons;
import com.johnbryce.beans.Customers;
import com.johnbryce.exceptions.CouponPurchasedException;
import com.johnbryce.exceptions.CustomerNotFoundException;
import com.johnbryce.exceptions.ExpiredCouponException;
import com.johnbryce.exceptions.NoCouponsException;

@Service
public class CustomersFacade extends ClientFacade {

	private int customerId;

	//** get customer object, by his email and password, if its not null, then his id equal to the current customer id.
	@Override
	public boolean login(String email, String password) {
		Customers temp = custDB.getCustomersByEmailAndPassword(email, password);
		if (temp != null) {
			customerId = temp.getId();
			return true;

		}
		return false;
	}

	//** purchase coupon method, the validation we need from the project preferences, update customer and update amount and the coupon over all.
	public void purchaseCoupon(Coupons coupon)
			throws ExpiredCouponException, NoCouponsException, CouponPurchasedException, CustomerNotFoundException {
		if (coupon.getEndDate().getTime() < Calendar.getInstance().getTimeInMillis()) {
			throw new ExpiredCouponException();
		}
		if (coupon.getAmount() == 0) {
			throw new NoCouponsException();
		}
		List<Coupons> purchasedCoupons = getCustomerCoupons();
		for (Coupons c : purchasedCoupons) {
			if (c.getId() == coupon.getId()) {
				throw new CouponPurchasedException();

			}
		}
		Customers cust = custDB.getOneCustomer(customerId);

		cust.getCustomerCoupons().add(coupon);
		custDB.updateCustomer(cust);

		coupon.setAmount(coupDB.getOneCoupon(coupon.getId()).getAmount() - 1);
		coupDB.updateCoupon(coupon);
	}

	//** make new array of customerCoupons, get all coupons and get all customers.
	//** make list of coupons, by one customer using customer id and get his coupons.
	//** if the customer Id equal to the current company Id, add to the CustomerCoupons list the customer and his coupons by the id.
	//** and return the new List with her new parameters.
	public List<Coupons> getCustomerCoupons() throws CustomerNotFoundException {
		List<Coupons> customerCoupons = new ArrayList<>();
		List<Customers> customers = custDB.getAllCustomers();
		List<Coupons> coupons = coupDB.getAllCoupons();
		for (Customers cus : customers) {
			List<Coupons> nums = custDB.getOneCustomer(cus.getId()).getCustomerCoupons();
			for (Coupons c : nums) {
				for (Coupons coupon : coupons) {
					if (cus.getId() == customerId) {
						if (c.getId() == coupon.getId()) {
							customerCoupons.add(coupon);
						}
					}
				}
			}
		}
		return customerCoupons;
	}
	
	//** using the older customer coupons and validate it by category.
	public List<Coupons> getCustomerCoupons(Categories category) throws CustomerNotFoundException {

		List<Coupons> getCouponsCategory = getCustomerCoupons();
		List<Coupons> couponsCategory = new ArrayList<>();
		for (Coupons c : getCouponsCategory) {
			if (c.getCategoryType().equals(category)) {
				couponsCategory.add(c);
			}
		}
		return couponsCategory;
	}
	
	//** using the older customer coupons and validate it by max price.
	public List<Coupons> getCustomerCoupons(double maxPrice) throws CustomerNotFoundException {

		List<Coupons> customerCoupons = getCustomerCoupons();
		List<Coupons> customerCouponsMaxPrice = new ArrayList<>();

		for (Coupons c : customerCoupons) {
			if (c.getPrice() <= maxPrice) {
				customerCouponsMaxPrice.add(c);
			}
		}
		return customerCouponsMaxPrice;
	}

	public Customers getCustomerDetails() throws CustomerNotFoundException {
		return custDB.getOneCustomer(customerId);

	}
	
	//** getting one customer by id, remove the coupon from the customerCoupons in that customer object.
	//** update and delete.
	public void deleteCouponsPurchase(Coupons coupon) throws CustomerNotFoundException {
		Customers cust = custDB.getOneCustomer(customerId);
		cust.getCustomerCoupons().remove(coupon);
		custDB.updateCustomer(cust);
//		coupDB.deleteCoupon(coupon.getId());
	}
	
	public List<Coupons> getAllCoupons(){
		return coupDB.getAllCoupons();
	}

}
