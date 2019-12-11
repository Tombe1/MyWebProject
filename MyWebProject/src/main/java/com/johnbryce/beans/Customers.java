package com.johnbryce.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Customers")
@Scope("prototype")
@Component
public class Customers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "FirstName", nullable = false)
	private String firstName;
	@Column(name = "LastName", nullable = false)
	private String lastName;
	@Column(name = "Email", nullable = false)
	private String email;
	@Column(name = "Password", nullable = false)
	private String password;
	@Column
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Coupons> customerCoupons = new ArrayList<>();
	
	public Customers() {
		super();
	}
	public Customers(int id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	public Customers(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	
	public Customers(int id, String firstName, String lastName, String email, String password,
			List<Coupons> customerCoupons) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.customerCoupons = customerCoupons;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public List<Coupons> getCustomerCoupons() {
		return customerCoupons;
	}
	public void setCustomerCoupons(List<Coupons> customerCoupons) {
		this.customerCoupons = customerCoupons;
	}
	@Override
	public String toString() {
		return "Customers [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", customerCoupons=" + customerCoupons + "]";
	}

}
