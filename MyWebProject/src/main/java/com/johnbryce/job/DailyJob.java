package com.johnbryce.job;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.johnbryce.beans.Coupons;
import com.johnbryce.db.CompaniesDBDAO;
import com.johnbryce.db.CouponsDBDAO;
import com.johnbryce.db.CustomersDBDAO;

@Component
@Scope("prototype")
public class DailyJob implements Runnable {
	
	@Autowired
	private CouponsDBDAO couponsDAO;
	@Autowired
	private CustomersDBDAO customerDAO;
	@Autowired
	private CompaniesDBDAO companiesDAO;

	private boolean quit;

	public DailyJob() {
		super();
	}

	public DailyJob(boolean quit) {
		super();
		this.quit = quit;
	}

	public DailyJob(CouponsDBDAO couponsDAO, boolean quit) {
		super();
		this.couponsDAO = couponsDAO;
		this.quit = quit;
	}

	public CouponsDBDAO getCouponsDAO() {
		return couponsDAO;
	}

	public void setCouponsDAO(CouponsDBDAO couponsDAO) {
		this.couponsDAO = couponsDAO;
	}

	public boolean isQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	@Override
	public void run() {
		while (quit == true) {
			Calendar cc = Calendar.getInstance();
			try {
				for (Coupons c : couponsDAO.getAllCoupons()) {
					if (c.getEndDate().after(cc.getTime())) {
						couponsDAO.deleteCoupon(c.getId());
					}
				}
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void stop() {
		if (quit == true) {
			quit = false;
		}
	}

}
