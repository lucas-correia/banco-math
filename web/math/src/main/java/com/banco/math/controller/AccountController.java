package com.banco.math.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.banco.math.model.Account;
import com.banco.math.repository.AccountRepository;
import com.banco.math.resource.AccountResource;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path = "/bank")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PutMapping(path = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(path = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> depositForAccount(@RequestBody AccountResource body) {

        Optional<Account> oAccount = accountRepository.findById(body.accountNumber);
        if(oAccount.isPresent()) {
            Account account = oAccount.get();
            if(body.value > 0) {
                account.setBalance(account.getBalance() + body.value);
                accountRepository.save(account);
                JSONObject resp = new JSONObject();
                resp.put("status", "Depósito realizado com sucesso!!");
                resp.put("saldo", account.getBalance());
                return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
            } else {
                JSONObject resp = new JSONObject();
                resp.put("status", "Valor a ser depositado deve ser maior que 0.");
                return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST);
            }
        } else {
            JSONObject resp = new JSONObject();
            resp.put("status", "Conta não encontrada!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/accountsList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accounstsList() {

        final Gson gson = new Gson();
        Iterable<Account> oAccount = accountRepository.findAll();
        List<Account> accounts = new ArrayList<>();
        if(oAccount != null) {
            oAccount.forEach(accounts::add);
            HashMap<String, List<Account>> maps = new HashMap<>();
            maps.put("accounts", accounts);
            return new ResponseEntity<>(gson.toJson(maps), HttpStatus.OK);
            
        } else {
            JSONObject resp = new JSONObject();
            resp.put("status", "Conta não encontrada!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accoountForId(@RequestParam int accountId) {
        Optional<Account> oAccount = accountRepository.findById(accountId);
        if(oAccount.isPresent()) {
            HashMap<String, Account> maps = new HashMap<>();
            maps.put("account", oAccount.get());
            final Gson gson = new Gson();
            return new ResponseEntity<String>(gson.toJson(maps), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
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
        for (int i = 0; i < 10; i++) {
            Account account = new Account(1500.38, "42732213810", "Lucas", 1);
            accountRepository.save(account);
        }
        JSONObject resp = new JSONObject();
        resp.put("status", "Contas cadastradas com sucesso!!");
        return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
    }


}
