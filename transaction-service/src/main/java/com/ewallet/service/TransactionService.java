package com.ewallet.service;

import com.ewallet.dto.CreateTransactionDto;
import com.ewallet.entity.Transaction;
import com.ewallet.entity.TransactionStatus;
import com.ewallet.repository.TransactionRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final String TRANSACTION_CREATED_TOPIC = "transaction-created";
    private static final String WALLET_UPDATED_TOPIC = "wallet-updated";

    private JSONParser jsonParser = new JSONParser();

    public Transaction send(CreateTransactionDto createTransactionDto){

        Transaction transaction = createTransactionDto.to();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction = this.transactionRepository.save(transaction);

        try {
            this.kafkaTemplate.send(TRANSACTION_CREATED_TOPIC, objectMapper.writeValueAsString(transaction));
        } catch (Exception e){
            e.printStackTrace();
        }

        return transaction;
    }

    @KafkaListener(topics = WALLET_UPDATED_TOPIC, groupId = "transaction-group")
    public void updateTransaction(String msg) throws ParseException {
        JSONObject eventData = (JSONObject) jsonParser.parse(msg);

        String walletUpdateStatus = (String) eventData.get("status");
        String externalTransactionId = (String) eventData.get("externalTransactionId");

        TransactionStatus transactionStatus = walletUpdateStatus.equals("SUCCESS") ? TransactionStatus.SUCCESS : TransactionStatus.FAILED;
        this.transactionRepository.updateTransaction(externalTransactionId, transactionStatus);
    }
}
