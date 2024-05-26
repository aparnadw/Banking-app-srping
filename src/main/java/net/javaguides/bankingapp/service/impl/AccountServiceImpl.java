package net.javaguides.bankingapp.service.impl;

import net.javaguides.bankingapp.dto.AccountDto;
import net.javaguides.bankingapp.entity.Account;
import net.javaguides.bankingapp.mapper.AccountMapper;
import net.javaguides.bankingapp.repository.AccountRepository;
import net.javaguides.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount((accountDto));
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto((savedAccount));
    }

    @Override
    public  AccountDto getAccountById(Long id){
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        return  AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account saveAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public AccountDto withdrow(Long id, double amount) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));

        if(account.getBalance() < amount) {
            throw  new RuntimeException("Insufficent amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account saveAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));

        accountRepository.deleteById(id);

    }
}
