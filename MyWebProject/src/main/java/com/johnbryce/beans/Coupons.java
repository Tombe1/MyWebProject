package com.johnbryce.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name = "Coupons")
@Scope("prototype")
@Component
public class Coupons {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// no optionals for nulls
	@ManyToOne(optional = false)
	// when its joining we want to call it company_id
	@JoinColumn(name = "company_id")
	// @JsonManagedReference // is the forward part of reference – the one that gets
	// serialized normally.
	private Companies companyId;
	@Column(name = "CategoryID")
	private Categories categories;
	@Column(name = "Title", nullable = false)
	private String title;
	@Column(name = "Description", nullable = true)
	private String description;
	@Column(name = "StartDate", nullable = false)
	private Date startDate;
	@Column(name = "EndDate", nullable = false)
	private Date endDate;
	@Column(name = "Amount", nullable = true)
	private int amount;
	@Column(name = "Price", nullable = false)
	private double price;
	@Column(name = "Image", nullable = true)
	private String image;

	public Coupons() {
		super();
	}

	public Coupons(Companies companyId, Categories categoryType, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.companyId = companyId;
		this.categories = categoryType;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupons(int id, Companies companyId, Categories categories, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.categories = categories;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Companies getCompanyId() {
		return companyId;
	}

//	@JsonValue
	public Categories getCategoryType() {
		return categories;
	}

	public void setCategoryType(Categories categoryType) {
		this.categories = categoryType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Coupons)) {
			return false;
		}
		if (this.id == ((Coupons) obj).getId()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Coupons [id=" + id + ", companyId=" + companyId.getId() + ", categoryType=" + categories + ", title="
				+ title + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", price=" + price + ", image=" + image + "]";
	}

	public void setCompany(Companies companyDetails) {
		this.companyId = companyDetails;
	}

}
