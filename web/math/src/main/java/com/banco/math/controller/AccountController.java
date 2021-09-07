package com.banco.math.controller;

import java.util.Optional;

import com.banco.math.model.Account;
import com.banco.math.repository.AccountRepository;
import com.banco.math.resource.AccountResource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/bank")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PutMapping(path = "/withdraw", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> withdrawFromAccount(@RequestBody AccountResource body) {

        Optional<Account> oAccount = accountRepository.findById(body.accountNumber);
        if(oAccount.isPresent()) {
            Account account = oAccount.get();
            if(account.getBalance() > body.value) {
                account.setBalance(account.getBalance() - body.value);
                accountRepository.save(account);
                JSONObject resp = new JSONObject();
                resp.put("status", "Saque realizado com sucesso!!");
                resp.put("saldo", account.getBalance());
                return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
            } else {
                JSONObject resp = new JSONObject();
                resp.put("status", "Saldo insuficiente!");
                return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST);
            }
        } else {
            JSONObject resp = new JSONObject();
            resp.put("status", "Conta não encontrada!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/withdraw", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> exemplo() {
        JSONObject resp = new JSONObject();
        resp.put("status", "Saque realizado com sucesso!!");
        return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/createAccount", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> createAccount() {
        Account account = new Account(1500.38, "42732213810", "Lucas", 1);
        accountRepository.save(account);
        JSONObject resp = new JSONObject();
        resp.put("status", "Conta cadastrada com sucesso!!");
        return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
    }


}
