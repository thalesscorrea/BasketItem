package com.interview.shoppingbasket;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.basket.PricingService;
import com.interview.shoppingbasket.checkout.CheckoutPipeline;
import com.interview.shoppingbasket.checkout.PaymentSummary;
import com.interview.shoppingbasket.checkout.steps.BasketConsolidationCheckoutStep;
import com.interview.shoppingbasket.checkout.steps.CheckoutStep;
import com.interview.shoppingbasket.checkout.steps.PromotionsRetrieveCheckoutStep;
import com.interview.shoppingbasket.checkout.steps.RetailPriceCheckoutStep;
import com.interview.shoppingbasket.promotion.Promotion;
import com.interview.shoppingbasket.promotion.PromotionType;
import com.interview.shoppingbasket.promotion.PromotionsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    PricingService pricingService;

    PromotionsService promotionsService;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
        pricingService = Mockito.mock(PricingService.class);
        promotionsService = Mockito.mock(PromotionsService.class);
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        basket = new Basket();
        basket.add("product1", "myproduct1", 2);
        basket.add("product1", "myproduct1", 1);
        basket.add("product2", "myproduct2", 3);

        checkoutPipeline.addStep(new BasketConsolidationCheckoutStep());
        checkoutPipeline.addStep(new PromotionsRetrieveCheckoutStep(promotionsService));
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("product1", PromotionType.TWOBYONE));
        promotions.add(new Promotion("product1", PromotionType.TENPERCENTDISCOUNT));
        promotions.add(new Promotion("product2", PromotionType.TWOBYONE));
        when(promotionsService.getPromotions(basket)).thenReturn(promotions);

        checkoutPipeline.addStep(new RetailPriceCheckoutStep(pricingService));
        when(pricingService.getPrice("product1")).thenReturn(5.0);
        when(pricingService.getPrice("product2")).thenReturn(10.0);

        PaymentSummary checkout = checkoutPipeline.checkout(basket);
        Assertions.assertEquals(29, checkout.getRetailTotal());
    }

}
