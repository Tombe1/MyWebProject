package com.johnbryce.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.johnbryce.beans.Coupons;
import com.johnbryce.beans.Customers;
import com.johnbryce.exceptions.CustomerNotFoundException;

@Repository
public class CustomersDBDAO {

	@Autowired
	private CustomersRepository custRepo;

	public void addCustomer(Customers customer) {
		custRepo.save(customer);
	}

	public void deleteCustomer(int id) {
		custRepo.deleteById(id);
	}

	public boolean updateCustomer(Customers customer) {
		if (custRepo.existsById(customer.getId())) {
			custRepo.save(customer);
			return true;
		}
		return false;

	}

	public List<Customers> getAllCustomers() {
		return custRepo.findAll();
	}

	public Customers getOneCustomer(int id) throws CustomerNotFoundException {
		Optional<Customers> opt = custRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CustomerNotFoundException();
	}

	public Customers getCustomersByEmailAndPassword(String email, String password) {
		return custRepo.getCustomersByEmailAndPassword(email, password);

	}
	
	public List<Customers> findCustomersByCouponId(Coupons cusCoupon){
		return custRepo.findCustomersByCustomerCoupons(cusCoupon);
	}

}
