package br.com.dbserver.restaurant.core.infrastructure.config;

public class RestaurantError {
    private final String message;

    public RestaurantError(String message) {
        this.message = message;
    }

    public RestaurantError() {
        this.message = "";
    }

    public String getMessage() {
        return message;
    }
}
