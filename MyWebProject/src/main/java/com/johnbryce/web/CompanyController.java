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

import com.johnbryce.beans.Categories;
import com.johnbryce.beans.Coupons;
import com.johnbryce.exceptions.CompanyNotFoundException;
import com.johnbryce.exceptions.CouponCantBeAddedException;
import com.johnbryce.exceptions.CouponCantBeUpdatedException;
import com.johnbryce.exceptions.CustomerNotFoundException;
import com.johnbryce.services.CompaniesFacade;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	@Autowired
	Map<String, Session> sessionMap;

	private void isTimeout(Session session, String token) {
		long now = System.currentTimeMillis();
		long delta = now - session.getLastAccessed();
		if (delta >= 30 * 60 * 1000) {
			sessionMap.remove(token);
			session = null;
		}
	}

	@PostMapping("/coupons/{token}")
	public ResponseEntity<Object> addCoupon(@PathVariable String token, @RequestBody Coupons coupon) {
		System.out.println(coupon.getCategoryType());
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				coupon.setCompany(facade.getCompanyDetails());
				facade.addCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (CouponCantBeAddedException | CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}

	@PutMapping("/coupons/{token}")
	public ResponseEntity<Object> updateCoupon(@PathVariable String token, @RequestBody Coupons coupon) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				coupon.setCompany(facade.getCompanyDetails());
				facade.updateCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (CouponCantBeUpdatedException | CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());

			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");

	}

	@DeleteMapping("/coupons/{token}/{id}")
	public ResponseEntity<Object> deleteCoupon(@PathVariable String token, @PathVariable int id) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				facade.deleteCoupon(id);
				return ResponseEntity.ok().build();
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/coupons/{token}")
	public ResponseEntity<Object> getCompanyCoupons(@PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyCoupons());
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	//@InitBinder
	@GetMapping("/coupons/{token}/category/{category}")
	public ResponseEntity<Object> getCompanyCouponsByCategory(@PathVariable String token,
			@PathVariable("category") Categories category) {
		// does enum getting requestbody or pathvariable?
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyCoupons(category));
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/coupons/{token}/price/{maxPrice}")
	public ResponseEntity<Object> getCompanyCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyCoupons(maxPrice));
			} catch (SQLException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CompanyNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("details/{token}")
	public ResponseEntity<Object> getCompanyDetails(@PathVariable String token) {
		// do i need to use the id or the token will do ?
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CompaniesFacade facade = (CompaniesFacade) session.getFacade();
			try {
				return ResponseEntity.ok(facade.getCompanyDetails());
			} catch (CompanyNotFoundException e) {
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
