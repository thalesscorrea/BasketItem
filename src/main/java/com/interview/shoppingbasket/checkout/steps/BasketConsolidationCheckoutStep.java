package com.interview.shoppingbasket.checkout.steps;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.checkout.CheckoutContext;

public class BasketConsolidationCheckoutStep implements CheckoutStep {

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        basket.consolidateItems();
    }

}
