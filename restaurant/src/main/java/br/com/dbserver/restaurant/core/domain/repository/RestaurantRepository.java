package br.com.dbserver.restaurant.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.dbserver.restaurant.core.domain.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    @Query("select r from Restaurant r where r.id not in (:ids)")
    List<Restaurant> findAllNotIn(@Param("ids") List<Long> ids);
}
