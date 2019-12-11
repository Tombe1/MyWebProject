package com.johnbryce.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.exceptions.InvalidClientTypeException;
import com.johnbryce.exceptions.WrongAdminDetailsException;
import com.johnbryce.exceptions.WrongCompanyDetailsException;
import com.johnbryce.exceptions.WrongCustomerDetailsException;
import com.johnbryce.loginManager.ClientType;
import com.johnbryce.loginManager.LoginManager;
import com.johnbryce.services.ClientFacade;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private Map<String, Session> sessionMap;

	@PostMapping("/login/{email}/{password}/{type}")
	public String login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType type) {

		String token = UUID.randomUUID().toString();
		try {
			ClientFacade facade = loginManager.login(email, password, type);
			Session session = new Session(facade, System.currentTimeMillis());
			sessionMap.put(token, session);
			System.out.println(token);
			return token;

		} catch (InvalidClientTypeException e) {
			return "Error: " + e.getMessage();
		} catch (WrongAdminDetailsException e) {
			return "Error: " + e.getMessage();
		} catch (WrongCompanyDetailsException e) {
			return "Error: " + e.getMessage();
		} catch (WrongCustomerDetailsException e) {
			return "Error: " + e.getMessage();
		}

	}

}
