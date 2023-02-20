package com.interview.shoppingbasket;

import com.interview.shoppingbasket.basket.Basket;
import com.interview.shoppingbasket.basket.BasketItem;
import com.interview.shoppingbasket.promotion.Promotion;
import com.interview.shoppingbasket.promotion.PromotionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketTest {
    @Test
    void emptyBasket() {
        Basket basket = new Basket();
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 0);
    }

    @Test
    void createBasketFullConstructor() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 1);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
    }

    @Test
    void createBasketWithMultipleProducts() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 10);
        basket.add("productCode3", "myProduct3", 10);

        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 3);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
        assertEquals(basketSize.get(1).getProductCode(), "productCode2");
        assertEquals(basketSize.get(1).getProductName(), "myProduct2");
        assertEquals(basketSize.get(1).getQuantity(), 10);
        assertEquals(basketSize.get(2).getProductCode(), "productCode3");
        assertEquals(basketSize.get(2).getProductName(), "myProduct3");
        assertEquals(basketSize.get(2).getQuantity(), 10);
    }

    @Test
    void assertConsolidateBasketKeepsNoMoreThanOneBasketItemWithSameProductCode() {
        Basket basket = new Basket();
        basket.add("productCode", "productName", 1);
        basket.add("productCode", "productName2", 2);
        basket.add("productCode2", "productName3", 3);
        basket.consolidateItems();
        Assertions.assertEquals(2, basket.getItems().size());
    }

    @Test
    void assertConsolidateBasketSumsItemsWithSameCode() {
        Basket basket = new Basket();
        basket.add("productCode", "productName", 1);
        basket.add("productCode", "productName2", 2);
        basket.consolidateItems();
        Assertions.assertEquals(3, basket.getItems().get(0).getQuantity());
    }

    @Test
    void createBasketWithMultiplePromotions() {
        Basket basket = new Basket();
        ArrayList<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("productCode1", PromotionType.FIFTYPERCENTDISCOUNT));
        promotions.add(new Promotion("productCode2", PromotionType.FIFTYPERCENTDISCOUNT));
        promotions.add(new Promotion("productCode3", PromotionType.FIFTYPERCENTDISCOUNT));
        promotions.add(new Promotion("productCode4", PromotionType.FIFTYPERCENTDISCOUNT));
        promotions.add(new Promotion("productCode4", PromotionType.TWOBYONE));
        basket.setPromotions(promotions);
        Assertions.assertEquals(5, basket.getPromotions().size());
    }

}
