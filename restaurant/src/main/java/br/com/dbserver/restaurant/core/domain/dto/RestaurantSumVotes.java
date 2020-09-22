package br.com.dbserver.restaurant.core.domain.dto;

import java.util.Objects;

import br.com.dbserver.restaurant.core.domain.Restaurant;

public class RestaurantSumVotes {
    private final Restaurant restaurant;
    private final Long numberOfVotes;

    public RestaurantSumVotes(Restaurant restaurant, Long numberOfVotes) {
        this.restaurant = restaurant;
        this.numberOfVotes = numberOfVotes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantSumVotes that = (RestaurantSumVotes) o;
        return Objects.equals(restaurant, that.restaurant) &&
                Objects.equals(numberOfVotes, that.numberOfVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant, numberOfVotes);
    }

    @Override
    public String toString() {
        return "RestaurantSumVotes{" +
                "restaurant=" + restaurant +
                ", numberOfVotes=" + numberOfVotes +
                '}';
    }
}
