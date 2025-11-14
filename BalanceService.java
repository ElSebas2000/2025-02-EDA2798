package scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import scr.model.Balance;

public class BalanceService implements IBalanceService {

    private final List<Balance> balances = new ArrayList<>();

    @Override
    public Balance save(Balance b) {
        deleteById(b.getDate().toString());
        balances.add(b);
        return b;
    }

    @Override
    public Optional<Balance> findById(String date) {
        return balances.stream()
                .filter(b -> b.getDate().toString().equals(date))
                .findFirst();
    }

    @Override
    public List<Balance> findAll() {
        return balances;
    }

    @Override
    public boolean deleteById(String date) {
        return balances.removeIf(b -> b.getDate().toString().equals(date));
    }
}
