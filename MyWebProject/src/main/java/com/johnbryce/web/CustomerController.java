package com.johnbryce.web;

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
import com.johnbryce.exceptions.CouponPurchasedException;
import com.johnbryce.exceptions.CustomerNotFoundException;
import com.johnbryce.exceptions.ExpiredCouponException;
import com.johnbryce.exceptions.NoCouponsException;
import com.johnbryce.services.CompaniesFacade;
import com.johnbryce.services.CustomersFacade;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

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
	
	@PostMapping("/purchase/{token}")
	public ResponseEntity<Object> purchaseCoupon(@PathVariable String token, @RequestBody Coupons coupon) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
			try {
				facade.purchaseCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (ExpiredCouponException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (NoCouponsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CouponPurchasedException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}		
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}
	
	@GetMapping("/coupons/{token}")
	public ResponseEntity<Object> getCustomerCoupons(@PathVariable String token) {
		// do need to get anything else like requestbody of couponds
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
				try {
					return ResponseEntity.ok(facade.getCustomerCoupons());
				} catch (CustomerNotFoundException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/coupons/{token}/category/{category}")
	public ResponseEntity<Object> getCustomerCouponsByCategory(@PathVariable String token,@PathVariable Categories category) {
		// does enum getting requestbody or pathvariable?
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
				try {
					return ResponseEntity.ok(facade.getCustomerCoupons(category));
				} catch (CustomerNotFoundException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}		
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}

	@GetMapping("/coupons/{token}/price/{maxPrice}")
	public ResponseEntity<Object> getCustomerCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();	
				try {
					return ResponseEntity.ok(facade.getCustomerCoupons(maxPrice));
				} catch (CustomerNotFoundException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}
	
	
	@GetMapping("details/{token}")
	public ResponseEntity<Object> getCustomerDetails(@PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
				try {
					return ResponseEntity.ok(facade.getCustomerDetails());
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
	
	@PutMapping("/deletepurchasedcoupon/{token}")
	public ResponseEntity<Object> deleteCouponsPurchase(@PathVariable String token, @RequestBody Coupons coupon){
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
//			CompaniesFacade company = (CompaniesFacade) session.getFacade();

			try {
//				coupon.setCompany(company.getCompanyDetails());
				facade.deleteCouponsPurchase(coupon);
				return ResponseEntity.ok().build();
			} catch (CustomerNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}		
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}
	
	@GetMapping("/allcoupons/{token}")
	public ResponseEntity<Object> getAllCoupons(@PathVariable String token) {
		Session session = sessionMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			session.setLastAccessed(System.currentTimeMillis());
			CustomersFacade facade = (CustomersFacade) session.getFacade();
			return ResponseEntity.ok(facade.getAllCoupons());
			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
	}
	
}
