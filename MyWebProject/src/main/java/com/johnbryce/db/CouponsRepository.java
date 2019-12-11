package com.johnbryce.db;


import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.beans.Coupons;

public interface CouponsRepository extends JpaRepository<Coupons, Integer> {

}
