package br.com.dbserver.restaurant.core.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.dto.RestaurantSumVotes;
import br.com.dbserver.restaurant.security.domain.User;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Long countByRestaurantAndDateTimeAfterAndDateTimeBefore(Restaurant restaurant,
                                                            LocalDateTime after,
                                                            LocalDateTime before);

    Long countByUserAndDateTimeAfterAndDateTimeBefore(User user,
                                                      LocalDateTime after,
                                                      LocalDateTime before);
    @Query("select new br.com.dbserver.restaurant.core.domain.dto.RestaurantSumVotes(v.restaurant, count(v)) " +
            "from Vote v where v.dateTime between :startDate and :endDate group by v.restaurant.id")
    List<RestaurantSumVotes> sumAllVotesByPeriod(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
