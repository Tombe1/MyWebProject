package com.johnbryce.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.johnbryce.beans.Companies;
import com.johnbryce.beans.Coupons;
import com.johnbryce.beans.Customers;
import com.johnbryce.exceptions.CompanyExistsException;
import com.johnbryce.exceptions.CompanyNotFoundException;
import com.johnbryce.exceptions.CustomerCantBeAddedException;
import com.johnbryce.exceptions.CustomerCantBeUpdatedException;
import com.johnbryce.exceptions.CustomerNotFoundException;

@Service
public class AdminFacade extends ClientFacade {

	// ** hard coded login method
	@Override
	public boolean login(String email, String password) {
		if (email.equalsIgnoreCase("admin@admin.com") && password.equals("admin")) {
			return true;
		}

		return false;
	}

	// ** validation of email and name from companies, and adding new company.
	public void addCompany(Companies company) throws CompanyExistsException {
		List<Companies> allCompanies = compDB.getAllCompanies();
		for (Companies comp : allCompanies) {
			if (comp.getEmail().equals(company.getEmail()) && comp.getName().equals(company.getName())) {
				throw new CompanyExistsException();
			}
			break;
		}
		compDB.addCompany(company);
	}

	// ** validation of name and id and updating company
	public void updateCompany(Companies company) throws CompanyExistsException {
		List<Companies> allCompanies = compDB.getAllCompanies();
		for (Companies comp : allCompanies) {
			if (comp.getName().equals(company.getName()) && comp.getId() == company.getId()) {
				compDB.updateCompany(company);
			} else
				throw new CompanyExistsException();

			break;
		}
	}

	// ** get one company, get list of company coupons, and getting list of
	// customerCoupons by coupon.
	// ** remove the customercoupons and update the customer.
	// ** clear the companycouponsm update the company and delete her by id.
	public void deleteCompany(int companyId) throws CompanyNotFoundException, CustomerNotFoundException {

		Companies company = compDB.getOneCompany(companyId);
		List<Coupons> couponsToRemove = company.getCoupons();
		for (Coupons c : couponsToRemove) {
			List<Customers> customersToUpdate = custDB.findCustomersByCouponId(c);
			for (Customers customer : customersToUpdate) {
				customer.getCustomerCoupons().remove(c);
				custDB.updateCustomer(customer);
			}
		}
		company.getCoupons().removeAll(couponsToRemove);
		compDB.updateCompany(company);
		compDB.deleteCompany(company.getId());
	}

	public List<Companies> getAllCompanies() {
		return compDB.getAllCompanies();
	}

	public Companies getOneCompany(int companyId) throws CompanyNotFoundException {
		return compDB.getOneCompany(companyId);
	}

	// ** validation of email, and add customer.
	public void addCustomer(Customers customer) throws CustomerCantBeAddedException {
		List<Customers> addCustomer = custDB.getAllCustomers();
		for (Customers c : addCustomer) {
			if (customer.getEmail().equals(c.getEmail())) {
				throw new CustomerCantBeAddedException();
			}
			break;
		}
		custDB.addCustomer(customer);
	}

	// ** validation of id, and update customer.
	public void updateCustomer(Customers customer) throws CustomerCantBeUpdatedException {
		List<Customers> updateCustomer = custDB.getAllCustomers();
		for (Customers c : updateCustomer) {
			if (customer.getId() == (c.getId())) {
				custDB.updateCustomer(customer);
			} else {
				throw new CustomerCantBeUpdatedException();
			}
			break;
		}
	}

	// ** get one customer by id, clear his coupons, update the customer, and delete
	// by id.
	public void deleteCustomer(int customerId) throws CustomerNotFoundException {
		Customers customer = custDB.getOneCustomer(customerId);
		customer.getCustomerCoupons().clear();
		custDB.updateCustomer(customer);
		custDB.deleteCustomer(customerId);
	}

	public List<Customers> getAllCustomers() throws SQLException {
		return custDB.getAllCustomers();
	}

	public Customers getOneCustomer(int id) throws SQLException, CustomerNotFoundException {
		return custDB.getOneCustomer(id);
	}
}
