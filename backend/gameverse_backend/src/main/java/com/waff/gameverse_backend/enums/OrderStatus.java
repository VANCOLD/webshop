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

}
