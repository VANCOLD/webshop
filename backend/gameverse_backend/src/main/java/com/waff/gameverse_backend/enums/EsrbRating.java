/**
 * This enum represents the various ESRB (Entertainment Software Rating Board) ratings
 * for video games. ESRB ratings categorize games based on their content and are used
 * to provide guidance to consumers, especially parents, regarding the suitability of a
 * game for different age groups.
 * Each enum constant corresponds to a specific ESRB rating category and includes
 * a human-readable name for that category.
 *
 * @see <a href="https://www.esrb.org/ratings-guide/" target="_blank">ESRB Ratings Guide</a>
 */
package com.waff.gameverse_backend.enums;

public enum EsrbRating {

    /**
     * Early Childhood rating, suitable for young children.
     */
    EARLY_CHILDHOOD("Early Childhood"),

    /**
     * Everyone rating, suitable for all age groups.
     */
    EVERYONE("Everyone"),

    /**
     * Everyone 10 and older rating, suitable for ages 10 and up.
     */
    EVERYONE_10("Everyone 10"),

    /**
     * Teen rating, suitable for teenagers.
     */
    TEEN("Teen"),

    /**
     * Mature 17+ rating, suitable for adults and those aged 17 and older.
     */
    MATURE17("Mature 17"),

    /**
     * Adults Only rating, restricted to adults only.
     */
    ADULTS_ONLY("Adults only"),

    /**
     * Rating Pending, indicating that the game has not yet received an ESRB rating.
     */
    RATING_PENDING("Rating Pending"),

    /**
     * Used for products that don't get a rating (consoles, merchandise, giftcards etc)
     */
    NO_RATING("No Rating");

    private final String name;

    EsrbRating(String name) {
        this.name = name;
    }

    /**
     * Get the human-readable name of the ESRB rating.
     *
     * @return The name of the ESRB rating.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get an ESRB rating enum constant based on its name.
     *
     * @param name The name of the ESRB rating.
     * @return The corresponding ESRB rating enum constant, or null if not found.
     */
    public static EsrbRating getEsrbRating(String name) {
        String buffer = name.replace(" ", "_").toUpperCase();
        try {
            return EsrbRating.valueOf(buffer);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Given string doesn't match any ESRB Rating!");
        }
    }
}
