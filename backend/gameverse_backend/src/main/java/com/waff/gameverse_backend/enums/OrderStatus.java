package com.waff.gameverse_backend.enums;

public enum OrderStatus {
    IN_PROGRESS("In Progress"), COMPLETED("Completed"), ERROR("Error");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public OrderStatus getOrderStatus(String name) {
        String toCheck = name.replaceAll(" ","_").toUpperCase();
        return OrderStatus.valueOf(toCheck);
    }

}
