package com.johnbryce.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.johnbryce.beans.Companies;
import com.johnbryce.exceptions.CompanyNotFoundException;

@Repository
public class CompaniesDBDAO {

	@Autowired
	private CompaniesRepository compRepo;

	public void addCompany(Companies company) {
		compRepo.save(company);
	}

	public void deleteCompany(int id) {
		compRepo.deleteById(id);
	}

	public boolean updateCompany(Companies company) {
		if (compRepo.existsById(company.getId())) {
			compRepo.save(company);
			return true;
		}
		return false;

	}

	public List<Companies> getAllCompanies() {
		return compRepo.findAll();
	}

	public Companies getOneCompany(int companyId) throws CompanyNotFoundException {
		Optional<Companies> opt = compRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CompanyNotFoundException();

	}

	public Companies getCompaniesByEmailAndPassword(String email, String password) {
		return compRepo.getCompaniesByEmailAndPassword(email, password);
	}
}
