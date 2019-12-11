package com.johnbryce.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.johnbryce.exceptions.InvalidClientTypeException;
import com.johnbryce.exceptions.WrongAdminDetailsException;
import com.johnbryce.exceptions.WrongCompanyDetailsException;
import com.johnbryce.exceptions.WrongCustomerDetailsException;
import com.johnbryce.services.AdminFacade;
import com.johnbryce.services.ClientFacade;
import com.johnbryce.services.CompaniesFacade;
import com.johnbryce.services.CustomersFacade;

@Component
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext ctxt;

	public ClientFacade login(String email, String password, ClientType type) throws InvalidClientTypeException,
			WrongAdminDetailsException, WrongCompanyDetailsException, WrongCustomerDetailsException {
		switch (type) {
		case Admin:
			AdminFacade adminFacade = ctxt.getBean(AdminFacade.class);
			if (adminFacade.login(email, password)) {
				return adminFacade;
			} else {
				throw new WrongAdminDetailsException();
			}
		case Company:
			CompaniesFacade companiesFacade = ctxt.getBean(CompaniesFacade.class);
			if (companiesFacade.login(email, password)) {
				return companiesFacade;
			} else {
				throw new WrongCompanyDetailsException();
			}
		case Customer:
			CustomersFacade customersFacade = ctxt.getBean(CustomersFacade.class);
			if (customersFacade.login(email, password)) {
				return customersFacade;
			} else {
				throw new WrongCustomerDetailsException();
			}

		default:
			throw new InvalidClientTypeException();
		}
	}
}
