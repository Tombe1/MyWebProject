package com.johnbryce.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Companies")
@Scope("prototype")
@Component
public class Companies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "CompanyName" , nullable = false)
//	@Column(unique = true)
	private String name;
	@Column(name = "CompanyEmail", nullable = false)
//	@Column(unique = true )
	private String email;
	@Column(name = "Password", nullable = false)
	private String password;
	@Column(name = "CompaniesCoupons", nullable = true)
	//fetch = FetchType.EAGER, cascade = CascadeType.ALL
	// not lazy, create them as soon as possible,
	// one company can have many coupons.
	// remove the coupon if the company deleted and refreshing and checking.
	// orphanRemoval = true -> It marks "child" entity to be removed when it's no longer referenced from the "parent" entity
	//mappedBy = > mapping the connection in the other object.
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "companyId" , cascade = CascadeType.MERGE , orphanRemoval = true)
	//@JsonBackReference // is the forward part of reference – the one that gets serialized normally.
	private List<Coupons> coupons = new ArrayList<>();

	public Companies() {
		super();
	}

	public Companies(String name, String email, String password, List<Coupons> coupons) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public Companies(int id, String name, String email, String password, List<Coupons> coupons) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public Companies(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Companies(int companyID, String name, String email, String password) {
		super();
		this.id = companyID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@JsonIgnore
	public List<Coupons> getCoupons() {
		return coupons;
	}

	public void addCoupons(Coupons coupon) {
		this.coupons.add(coupon);
	}

	public void removeCoupon(Coupons coupon) {
		this.coupons.remove(coupon);
	}

	@Override
	public String toString() {
		return "Companies [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

}
