package scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import scr.model.Loans;

public class LoansService implements ILoansService {

    private final List<Loans> loans = new ArrayList<>();

    @Override
    public Loans save(Loans l) {
        deleteById(l.getDate().toString());
        loans.add(l);
        return l;
    }

    @Override
    public Optional<Loans> findById(String date) {
        return loans.stream()
                .filter(l -> l.getDate().toString().equals(date))
                .findFirst();
    }

    @Override
    public List<Loans> findAll() {
        return loans;
    }

    @Override
    public boolean deleteById(String date) {
        return loans.removeIf(l -> l.getDate().toString().equals(date));
    }
}
