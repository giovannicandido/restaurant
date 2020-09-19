package br.com.dbserver.restaurant.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    // todo retrieve only restaurants that have not been selected this week
    @Query("select r from Restaurant r")
    List<Restaurant> findAllNotSelectedThisWeek();
}
