package com.ewallet.controller;

import com.ewallet.dto.CreateTransactionDto;
import com.ewallet.entity.Transaction;
import com.ewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public Transaction send(@RequestBody CreateTransactionDto createTransactionDto){
        return transactionService.send(createTransactionDto);
    }
}
