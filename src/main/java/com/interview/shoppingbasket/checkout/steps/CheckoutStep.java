package com.interview.shoppingbasket.checkout.steps;


import com.interview.shoppingbasket.checkout.CheckoutContext;

public interface CheckoutStep {
    void execute(CheckoutContext checkoutContext);
}
