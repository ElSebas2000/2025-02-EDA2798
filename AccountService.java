package scr.services;

import java.util.List;
import java.util.Optional;

import scr.model.Account;
import scr.repositories.AccountRepository;

public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountService() {
		this(new AccountRepository());
	}

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository=accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(String accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public boolean deleteById(String accountNumber) {
        return accountRepository.deleteById(accountNumber);
    }

}