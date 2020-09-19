package br.com.dbserver.restaurant.core.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Long countByRestaurantAndDateTimeAfterAndDateTimeBefore(Restaurant restaurant,
                                                             LocalDateTime after,
                                                             LocalDateTime before);
}
