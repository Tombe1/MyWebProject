package com.johnbryce.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.johnbryce.beans.Categories;
import com.johnbryce.beans.Companies;
import com.johnbryce.beans.Coupons;
import com.johnbryce.beans.Customers;
import com.johnbryce.exceptions.CompanyNotFoundException;
import com.johnbryce.exceptions.CouponCantBeAddedException;
import com.johnbryce.exceptions.CouponCantBeUpdatedException;
import com.johnbryce.exceptions.CustomerNotFoundException;

@Service
public class CompaniesFacade extends ClientFacade {

	private int companyId;

	//** get company object, by his email and password, if its not null, then his id equal to the current companyId.
	@Override
	public boolean login(String email, String password) {
		Companies temp = compDB.getCompaniesByEmailAndPassword(email, password);
		if (temp != null) {
			companyId = temp.getId();
			return true;

		}
		return false;
	}

	//** validation of his title and after it is id, then update by companyId and add the coupon.
	public void addCoupon(Coupons coupon) throws CouponCantBeAddedException {
		List<Coupons> addCoupon = coupDB.getAllCoupons();
		for (Coupons c : addCoupon) {
			if (coupon.getTitle().equals(c.getTitle())) {
				if (coupon.getCompanyId().getId() == companyId) {
					throw new CouponCantBeAddedException();
				}
				break;
			}

		}
		compDB.updateCompany(coupon.getCompanyId());
		coupDB.addCoupon(coupon);
	}

	//** validation of coupon id and company id, and then update coupon.
	public boolean updateCoupon(Coupons coupon) throws CouponCantBeUpdatedException {

		List<Coupons> updateCoupon = coupDB.getAllCoupons();
		for (Coupons c : updateCoupon) {
			if (coupon.getId() == c.getId() && coupon.getCompanyId() == c.getCompanyId()) {
				throw new CouponCantBeUpdatedException();
			}
			break;
		}
		return coupDB.updateCoupon(coupon);
	}

	//** getting one coupon by id. making company object and compare her to the coupon we want to delete with his companyId.
	//** on this company object we want to remove the coupon we brought. and update the company.
	//** getting list of all customers, checking if its contain the coupon we want to delete, and then remove it.
	//** after it update the customer, and delete the coupon by id.
	public void deleteCoupon(int id) throws CompanyNotFoundException, CustomerNotFoundException {
		Coupons couponToDelete = coupDB.getOneCoupon(id);
		Companies comp = couponToDelete.getCompanyId();
		comp.removeCoupon(couponToDelete);
		compDB.updateCompany(comp);

		List<Customers> customers = custDB.getAllCustomers();
		for (Customers customer : customers) {
			if (customer.getCustomerCoupons().contains(couponToDelete)) {
				customer.getCustomerCoupons().remove(couponToDelete);
				custDB.updateCustomer(customer);

			}
		}
		coupDB.deleteCoupon(id);

	}

	//** make new array of companyCoupons, get all companies and get all coupons.
	//** make list of coupons, by one company using companyId and get her coupons.
	//** if the company Id equal to the current company Id, add to the CompanyCoupons list the company and her coupons by the id.
	//** and return the new List with her new parameters.
	public List<Coupons> getCompanyCoupons() throws CompanyNotFoundException {
		List<Coupons> companyCoupons = new ArrayList<>();
		List<Companies> companies = compDB.getAllCompanies();
		List<Coupons> coupons = coupDB.getAllCoupons();

		for (Companies com : companies) {
			List<Coupons> nums = compDB.getOneCompany(com.getId()).getCoupons();
			for (Coupons coup : nums) {
				for (Coupons coupon : coupons) {
					if (com.getId() == companyId) {
						if (coup.getId() == coupon.getId()) {
							companyCoupons.add(coup);

						}
					}
				}
			}

		}
		return companyCoupons;
	}

	//** using the older company coupons and validate it by category.
	public List<Coupons> getCompanyCoupons(Categories category) throws CompanyNotFoundException {
		List<Coupons> getCouponsCategory = getCompanyCoupons();
		List<Coupons> couponsCategory = new ArrayList<>();
		for (Coupons c : getCouponsCategory) {
			if (c.getCategoryType().equals(category)) {
				couponsCategory.add(c);
			}
		}
		return couponsCategory;
	}

	//** using the older company coupons and validate it by max price.
	public List<Coupons> getCompanyCoupons(double maxPrice) throws SQLException, CompanyNotFoundException {
		List<Coupons> getCompanyCouponsMaxPrice = getCompanyCoupons();
		List<Coupons> companyCouponsMaxPrice = new ArrayList<>();

		for (Coupons c : getCompanyCouponsMaxPrice) {
			if (c.getPrice() <= maxPrice) {
				companyCouponsMaxPrice.add(c);
			}
		}
		return companyCouponsMaxPrice;
	}
	
	//** get company details by company Id.
	public Companies getCompanyDetails() throws CompanyNotFoundException {
		return compDB.getOneCompany(companyId);
	}
}