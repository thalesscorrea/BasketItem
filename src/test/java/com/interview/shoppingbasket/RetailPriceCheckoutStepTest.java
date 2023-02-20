package com.interview.shoppingbasket;

import static org.mockito.Mockito.when;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.basket.BasketItem;
import com.interview.shoppingbasket.basket.PricingService;
import com.interview.shoppingbasket.checkout.CheckoutContext;
import com.interview.shoppingbasket.checkout.steps.RetailPriceCheckoutStep;
import com.interview.shoppingbasket.promotion.Promotion;
import com.interview.shoppingbasket.promotion.PromotionType;
import com.interview.shoppingbasket.promotion.PromotionsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RetailPriceCheckoutStepTest {

    PricingService pricingService;
    CheckoutContext checkoutContext;
    PromotionsService promotionsService;
    Basket basket;

    @BeforeEach
    void setup() {
        pricingService = Mockito.mock(PricingService.class);
        promotionsService = Mockito.mock(PromotionsService.class);
        checkoutContext = Mockito.mock(CheckoutContext.class);
        basket = new Basket();

        when(checkoutContext.getBasket()).thenReturn(basket);
    }

    @Test
    void setPriceZeroForEmptyBasket() {

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);

        Mockito.verify(checkoutContext).setRetailPriceTotal(0.0);
    }

    @Test
    void setPriceOfProductToBasketItem() {

        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);
        Mockito.verify(checkoutContext).setRetailPriceTotal(3.99*10+2*10);

    }

    @Test
    void assertPricePromotionApplyCorrectly() {
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);
        Promotion promotion = new Promotion("product1", PromotionType.TWOBYONE);
        BasketItem basketItem = new BasketItem("product1", "myproduct1", 3, 5);
        Assertions.assertEquals(10, retailPriceCheckoutStep.applyPromotion(promotion, basketItem, 15));
    }

    @Test
    void assertPricePromotionApplyCorrectlyToMultiplePromotions() {
        basket.add("product1", "myproduct1", 3);
        basket.add("product2", "myproduct2", 3);

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("product1", PromotionType.TWOBYONE));
        promotions.add(new Promotion("product1", PromotionType.TENPERCENTDISCOUNT));
        promotions.add(new Promotion("product2", PromotionType.TWOBYONE));
        basket.setPromotions(promotions);

        CheckoutContext checkoutContext = new CheckoutContext(basket);

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);
        when(pricingService.getPrice("product1")).thenReturn(5.0);
        when(pricingService.getPrice("product2")).thenReturn(5.0);
        retailPriceCheckoutStep.execute(checkoutContext);

        Assertions.assertEquals(19, checkoutContext.paymentSummary().getRetailTotal());
    }



}
