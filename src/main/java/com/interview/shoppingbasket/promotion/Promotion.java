package com.interview.shoppingbasket.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private String productCode;

    private PromotionType promotionType;
}
