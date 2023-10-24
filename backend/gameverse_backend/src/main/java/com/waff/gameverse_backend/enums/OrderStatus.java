package com.waff.gameverse_backend.enums;

public enum OrderStatus {
    ORDERED("Ordered"), SHIPPED("Shipped"), CANCELED("Canceled");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus getOrderStatus(String name) {
        try {
            return OrderStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Given string doesn't match any Order Status!");
        }
    }
}
