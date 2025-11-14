package scr.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import scr.model.Account;

public class AccountRepository {

    private final List<Account> storage = new ArrayList<>();

    public AccountRepository() {
        initData();
    }

    //  DATOS INICIALES (10 cuentas)

    private void initData() {
        storage.add(new Account("ACC001", "Ana Perez", "ana.perez@example.com", "3000000001", "Savings",  "Calle 1"));
        storage.add(new Account("ACC002", "Luis Gomez", "luis.gomez@example.com", "3000000002", "Checking","Calle 2"));
        storage.add(new Account("ACC003", "María Ruiz", "maria.ruiz@example.com","3000000003","Savings", "Calle 3"));
        storage.add(new Account("ACC004", "Carlos Díaz","carlos.diaz@example.com","3000000004","Checking","Calle 4"));
        storage.add(new Account("ACC005", "Sofia Morales","sofia.morales@example.com","3000000005","Savings","Calle 5"));
        storage.add(new Account("ACC006", "Diego Torres","diego.torres@example.com","3000000006","Checking","Calle 6"));
        storage.add(new Account("ACC007", "Lucia Herrera","lucia.herrera@example.com","3000000007","Savings","Calle 7"));
        storage.add(new Account("ACC008", "Miguel Ramos","miguel.ramos@example.com","3000000008","Checking","Calle 8"));
        storage.add(new Account("ACC009", "Natalia Vega","natalia.vega@example.com","3000000009","Savings","Calle 9"));
        storage.add(new Account("ACC010", "Jorge Castro","jorge.castro@example.com","3000000010","Checking","Calle 10"));
    }

    //  SAVE (insertar o actualizar)

    public Account save(Account account) {

        if (account == null || account.getAccountNumber() == null) {
            throw new IllegalArgumentException("Account o accountNumber no puede ser null");
        }

        // reemplazar si ya existe, agregar si no existe
        storage.removeIf(a -> a.getAccountNumber().equals(account.getAccountNumber()));
        storage.add(account);

        return account;
    }

    //  BUSCAR POR ID

    public Optional<Account> findById(String accountNumber) {
        if (accountNumber == null) return Optional.empty();
        return storage.stream()
                .filter(a -> accountNumber.equals(a.getAccountNumber()))
                .findFirst();
    }

    //  LISTAR TODO

    public List<Account> findAll() {
        return new ArrayList<>(storage); // protección contra modificaciones externas
    }


    //  ELIMINAR POR ID

    public boolean deleteById(String accountNumber) {
        return findById(accountNumber)
                .map(storage::remove)
                .orElse(false);
    }

    //  EXISTE?

    public boolean existsById(String accountNumber) {
        if (accountNumber == null) return false;
        return storage.stream().anyMatch(a -> accountNumber.equals(a.getAccountNumber()));
    }

}
