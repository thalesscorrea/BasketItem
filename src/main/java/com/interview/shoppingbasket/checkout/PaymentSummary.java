package com.interview.shoppingbasket.checkout;

public class PaymentSummary {
    private final double retailPriceTotal;

    public PaymentSummary(double retailPriceTotal) {
        this.retailPriceTotal = retailPriceTotal;
    }

    public double getRetailTotal() {
        return retailPriceTotal;
    }
}
