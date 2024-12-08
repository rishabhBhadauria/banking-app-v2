package net.javaguide.banking.controller;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.service.AccountSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/account/")
public class AccountController {
    @Autowired
    private AccountSvc accountSvc;

    @PostMapping("create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountSvc.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(accountSvc.getAccount(id), HttpStatus.OK);
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String,Double> request) {
        Double amount = request.get("amount");
        return new ResponseEntity<>(accountSvc.deposit(id,amount), HttpStatus.CREATED);
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String,Double> request) {
        Double amount = request.get("amount");
        return new ResponseEntity<>(accountSvc.withdraw(id,amount), HttpStatus.CREATED);
    }

}
