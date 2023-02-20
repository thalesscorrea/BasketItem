package com.interview.shoppingbasket.basket;

import com.interview.shoppingbasket.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        Map<String, BasketItem> productItems = items.stream()
                .collect(Collectors.toMap(
                        BasketItem::getProductCode,
                        Function.identity(),
                        (item1, item2) -> new BasketItem(
                                item1.getProductCode(),
                                item1.getProductName(),
                                item1.getQuantity() + item2.getQuantity(),
                                item1.getProductRetailPrice()
                        )
                ));
        this.items = new ArrayList<>(productItems.values());
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
