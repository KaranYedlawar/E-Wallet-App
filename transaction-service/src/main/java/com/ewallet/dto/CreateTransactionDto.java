package com.ewallet.dto;


import com.ewallet.entity.Transaction;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionDto {

    private String sender;
    private String receiver;
    private Long amount;
    private String comment;

    public Transaction to(){
        return Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .comment(comment)
                .amount(amount)
                .build();
    }
}
