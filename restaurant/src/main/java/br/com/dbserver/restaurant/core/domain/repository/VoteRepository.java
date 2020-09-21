package br.com.dbserver.restaurant.core.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.security.domain.User;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Long countByRestaurantAndDateTimeAfterAndDateTimeBefore(Restaurant restaurant,
                                                            LocalDateTime after,
                                                            LocalDateTime before);

    Long countByUserAndDateTimeAfterAndDateTimeBefore(User user,
                                                      LocalDateTime after,
                                                      LocalDateTime before);
}
