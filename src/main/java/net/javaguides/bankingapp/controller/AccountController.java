package net.javaguides.bankingapp.controller;

import net.javaguides.bankingapp.dto.AccountDto;
import net.javaguides.bankingapp.entity.Account;
import net.javaguides.bankingapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto>  getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit REST API
    @PutMapping("/{id}/deposit")
    public  ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Withdraw REST
    @PutMapping("/{id}/withdraw")
    public  ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                                @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdrow(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Get All account REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccount(){
        List<AccountDto> accountDto = accountService.getAllAccount();
        return ResponseEntity.ok(accountDto);
    }

    // Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted ");
    }
}
