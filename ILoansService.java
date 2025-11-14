package scr.services;

import java.util.List;
import java.util.Optional;

import scr.model.Loans;

public interface ILoansService {
	Loans save(Loans loan);
	Optional<Loans> findById(String id); // id = date.toString()
	List<Loans> findAll();
	boolean deleteById(String id);
}