package br.com.dbserver.restaurant.core.infrastructure.config;

public class RestaurantError {
    private final String message;

    public RestaurantError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
