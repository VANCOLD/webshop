package com.waff.gameverse_backend.enums;

public enum EsrbRating {

    EARLY_CHILDHOOD("Early Childhood"), EVERYONE("Everyone"), EVERYONE_10("Everyone 10"),
    TEEN("Teen"), MATURE17("Mature 17"), ADULTS_ONLY("Adults only"), RATING_PENDING("Rating Peding");

    private final String name;

    private EsrbRating(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public EsrbRating getEsrbRating(String name) {
        String buffer = name.replace(" ", "_").toUpperCase();
        return EsrbRating.valueOf(buffer);
    }
}
