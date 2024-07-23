package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Customer {
    private String webShopId;
    private String customerId;
    private String name;
    private String address;
}
