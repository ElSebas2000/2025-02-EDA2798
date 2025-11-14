package scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import scr.model.Cards;

public class CardService implements ICardService {

    private final List<Cards> cards = new ArrayList<>();

    @Override
    public Cards save(Cards c) {
        deleteById(c.getCardNumber());
        cards.add(c);
        return c;
    }

    @Override
    public Optional<Cards> findById(String cardNumber) {
        return cards.stream()
                .filter(c -> c.getCardNumber().equals(cardNumber))
                .findFirst();
    }

    @Override
    public List<Cards> findAll() {
        return cards;
    }

    @Override
    public boolean deleteById(String cardNumber) {
        return cards.removeIf(c -> c.getCardNumber().equals(cardNumber));
    }
}
