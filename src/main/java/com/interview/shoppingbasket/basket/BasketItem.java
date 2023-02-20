package com.interview.shoppingbasket.basket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItem {
    private String productCode;
    private String productName;
    private int quantity;
    private double productRetailPrice;
}
