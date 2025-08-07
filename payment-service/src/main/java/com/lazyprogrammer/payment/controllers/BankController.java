package com.lazyprogrammer.payment.controllers;

import com.lazyprogrammer.payment.models.AccountsDto;
import com.lazyprogrammer.payment.repositories.BankRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @GetMapping("/accounts")
    public List<AccountsDto> getAllAccounts() {
        return BankRepository.getAllAccounts();
    }
}
