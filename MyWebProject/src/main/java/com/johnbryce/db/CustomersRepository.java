package com.johnbryce.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.beans.Coupons;
import com.johnbryce.beans.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer> {
	
	Customers getCustomersByEmailAndPassword(String email ,String password);
	List<Customers> findCustomersByCustomerCoupons(Coupons cusCoupon);


}
