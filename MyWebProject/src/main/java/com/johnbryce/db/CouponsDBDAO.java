package com.johnbryce.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.johnbryce.beans.Coupons;

@Repository
public class CouponsDBDAO {

	@Autowired
	private CouponsRepository coupRepo;

	public void addCoupon(Coupons coupon) {
		coupRepo.save(coupon);
	}

	public void deleteCoupon(int id) {
		coupRepo.deleteById(id);
	}

	public boolean updateCoupon(Coupons coupon) {
		if (coupRepo.existsById(coupon.getId())) {
			coupRepo.save(coupon);
			return true;
		}
		return false;

	}

	public List<Coupons> getAllCoupons() {
		return coupRepo.findAll();
	}

	public Coupons getOneCoupon(int id) {
		Optional<Coupons> opt = coupRepo.findById(id);
		if (opt.isPresent()) {
		}
		return opt.get();
	}
}
