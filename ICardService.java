package scr.services;

import java.util.List;
import java.util.Optional;

import scr.model.Cards;

public interface ICardService {
	Cards save(Cards card);
	Optional<Cards> findById(String cardNumber);
	List<Cards> findAll();
	boolean deleteById(String cardNumber);
}