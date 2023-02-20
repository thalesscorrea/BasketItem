package com.interview.shoppingbasket.checkout;

import com.interview.shoppingbasket.basket.Basket;

public class CheckoutContext {
    private final Basket basket;

    private double retailPriceTotal = 0.0;

    public void setRetailPriceTotal(double retailPriceTotal) {
        this.retailPriceTotal = retailPriceTotal;
    }

    public CheckoutContext(Basket basket) {
        this.basket = basket;
    }

    public PaymentSummary paymentSummary() {
        return new PaymentSummary(retailPriceTotal);
    }

    public Basket getBasket() {
        return basket;
    }
}
