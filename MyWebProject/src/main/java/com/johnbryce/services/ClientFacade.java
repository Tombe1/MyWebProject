package com.johnbryce.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.johnbryce.db.CompaniesDBDAO;
import com.johnbryce.db.CouponsDBDAO;
import com.johnbryce.db.CustomersDBDAO;

public abstract class ClientFacade {
	
	@Autowired
	protected CompaniesDBDAO compDB;
	@Autowired
	protected CouponsDBDAO coupDB;
	@Autowired
	protected CustomersDBDAO custDB;
	
	public abstract boolean login(String email, String password);

}
