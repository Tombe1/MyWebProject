package com.johnbryce.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.johnbryce.beans.Companies;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

	Companies getCompaniesByEmailAndPassword(String email, String password);

}
