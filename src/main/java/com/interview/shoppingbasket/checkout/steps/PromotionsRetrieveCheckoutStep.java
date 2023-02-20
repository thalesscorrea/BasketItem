package com.interview.shoppingbasket.checkout.steps;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.checkout.CheckoutContext;
import com.interview.shoppingbasket.promotion.PromotionsService;

public class PromotionsRetrieveCheckoutStep implements CheckoutStep {

    private final PromotionsService promotionsService;

    public PromotionsRetrieveCheckoutStep(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        basket.setPromotions(promotionsService.getPromotions(basket));
    }
}
