package com.johnbryce.web;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.beans.Companies;
import com.johnbryce.beans.Customers;
import com.johnbryce.exceptions.CompanyExistsException;
import com.johnbryce.exceptions.CompanyNotFoundException;
import com.johnbryce.exceptions.CustomerCantBeAddedException;
import com.johnbryce.exceptions.CustomerCantBeUpdatedException;
import com.johnbryce.exceptions.CustomerNotFoundException;
import com.johnbryce.services.AdminFacade;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	Map<String, Session> sessionMap;

	private void isTimeout(Session session, String token) {
		long now = System.currentTimeMillis();
		long delta = now - session.getLastAccessed();
		if (delta >= 30*60*1000) {
			sessionMap.remove(token);
			session = null;
		}
	}

	@PostMapping("/companies/{token}")
	public ResponseEntity<Object> addCompany(@PathVariable String token, @RequestBody Companies company) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.addCompany(company);
				return ResponseEntity.ok(company);
			} catch (CompanyExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

	@PutMapping("/companies/{token}")
	public ResponseEntity<Object> updateCompany(@PathVariable String token, @RequestBody Companies company) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				System.out.println(company);
				facade.updateCompany(company);
				return ResponseEntity.ok(company);
			} catch (CompanyExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

	@DeleteMapping("/companies/{token}/{id}")
	public ResponseEntity<Object> deleteCompany(@PathVariable String token, @PathVariable int id) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.deleteCompany(id);
				return ResponseEntity.ok().build();
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());

			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		
	}

	@GetMapping("/companies/{token}")
	public ResponseEntity<Object> getAllCompanies(@PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			return ResponseEntity.ok(facade.getAllCompanies());
			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/companies/{token}/{id}")
	public ResponseEntity<Object> getOneCompany(@PathVariable int id, @PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getOneCompany(id));
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@PostMapping("/customers/{token}")
	public ResponseEntity<Object> addCustomer(@PathVariable String token, @RequestBody Customers customer) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.addCustomer(customer);
				return ResponseEntity.ok(customer);

			} catch (CustomerCantBeAddedException e) {
				return ResponseEntity.badRequest().body(e.getMessage());

			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

	@PutMapping("/customers/{token}")
	public ResponseEntity<Object> updateCustomer(@PathVariable String token, @RequestBody Customers customer) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.updateCustomer(customer);
				return ResponseEntity.ok(customer);
			} catch (CustomerCantBeUpdatedException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@DeleteMapping("/customers/{token}/{id}")
	public ResponseEntity<Object>  deleteCustomer(@PathVariable int id, @PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				facade.deleteCustomer(id);
				return ResponseEntity.ok().build();
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");

	}

	@GetMapping("/customers/{token}")
	public ResponseEntity<Object> getAllCustomers(@PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok( facade.getAllCustomers());
				
			} catch (SQLException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/customers/{token}/{id}")
	public ResponseEntity<Object> getOneCustomer(@PathVariable int id, @PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			AdminFacade facade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getOneCustomer(id));
			} catch (SQLException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@PostMapping("/logout/{token}")
	public void logout(@PathVariable String token) {
		sessionMap.remove(token);

	}
}
