package com.interview.shoppingbasket.checkout.steps;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.basket.BasketItem;
import com.interview.shoppingbasket.basket.PricingService;
import com.interview.shoppingbasket.checkout.CheckoutContext;
import com.interview.shoppingbasket.promotion.Promotion;

import java.util.List;
import java.util.stream.Collectors;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private final PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        retailTotal = 0.0;

        for (BasketItem basketItem : basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);
            double totalPrice = quantity * price;
            List<Promotion> promotions = basket.getPromotions().stream()
                    .filter(p -> p.getProductCode().equals(basketItem.getProductCode()))
                    .collect(Collectors.toList());
            for (Promotion promotion : promotions) {
                totalPrice = applyPromotion(promotion, basketItem, totalPrice);
            }

            retailTotal += totalPrice;
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem item, double totalPrice) {
        switch (promotion.getPromotionType()) {
            case FIFTYPERCENTDISCOUNT:
                totalPrice = totalPrice * 0.5;
                break;
            case TENPERCENTDISCOUNT:
                totalPrice = totalPrice * 0.9;
                break;
            case TWOBYONE:
                double itemQuantity = item.getQuantity();
                totalPrice = totalPrice * ((itemQuantity - item.getQuantity() / 2) / itemQuantity);
                break;
        }
        return totalPrice;
    }
}
