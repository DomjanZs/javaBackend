package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Payments {
    private String webShopId;
    private String customerId;
    private String paymentMethod;
    private int amount;
    private String bankAccountNumber;
    private String cardNumber;
    private LocalDate paymentDate;
}

