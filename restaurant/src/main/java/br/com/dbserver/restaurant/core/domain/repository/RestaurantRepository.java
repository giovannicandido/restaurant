package br.com.dbserver.restaurant.core.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
