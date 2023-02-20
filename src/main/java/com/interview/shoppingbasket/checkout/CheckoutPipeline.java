package com.interview.shoppingbasket.checkout;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.checkout.steps.CheckoutStep;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPipeline {

    private final List<CheckoutStep> steps = new ArrayList<>();

    public PaymentSummary checkout(Basket basket) {
        CheckoutContext checkoutContext = new CheckoutContext(basket);
        for (CheckoutStep checkoutStep : steps) {
            checkoutStep.execute(checkoutContext);
        }

        return checkoutContext.paymentSummary();
    }

    public void addStep(CheckoutStep checkoutStep) {
        steps.add(checkoutStep);
    }
}
