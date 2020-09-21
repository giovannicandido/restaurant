package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.service.RestaurantSumVotes;
import br.com.dbserver.restaurant.security.domain.User;

@DataJpaTest
@Sql({"/sql/clear-database.sql","/sql/list-restaurant.sql","/sql/votes.sql"})
class VoteRepositoryTest {
    @Autowired
    private VoteRepository repository;

    @Test
    void countByRestaurantAndDateTimeAfterAndDateTimeBefore() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        LocalDateTime after = LocalDateTime.of(2020, 9, 19, 0, 0);
        LocalDateTime before = LocalDateTime.of(2020, 9, 19, 12, 0);

        Long count = repository.countByRestaurantAndDateTimeAfterAndDateTimeBefore(restaurant, after, before);
        assertThat(count).isEqualTo(1);

    }

    @Test
    void countByUserAndDateTimeAfterAndDateTimeBefore() {
        User user = new User();
        user.setId(1L);
        LocalDateTime afterDate = LocalDateTime.of(2020, 9, 19, 0, 0);
        LocalDateTime beforeDate = LocalDateTime.of(2020, 9, 19, 12, 0);
        Long count = repository.countByUserAndDateTimeAfterAndDateTimeBefore(user, afterDate, beforeDate);
        assertThat(count).isEqualTo(1);

        LocalDateTime afterDate2 =  LocalDateTime.of(2020, 9, 18, 0, 0);
        LocalDateTime beforeDate2 =  LocalDateTime.of(2020, 9, 19, 23, 59);
        Long count2 = repository.countByUserAndDateTimeAfterAndDateTimeBefore(user, afterDate2, beforeDate2);
        assertThat(count2).isEqualTo(2);
    }

    @Test
    void sumAllVotesByPeriod() {
        LocalDateTime startDate = LocalDateTime.parse("2020-09-18T09:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-09-19T13:00:00");
        List<RestaurantSumVotes> restaurantSumVotes = repository.sumAllVotesByPeriod(startDate, endDate);
        assertThat(restaurantSumVotes).hasSize(2);

        Restaurant re1 = new Restaurant();
        re1.setId(1L);
        RestaurantSumVotes rs1 = new RestaurantSumVotes(re1, 2L);
        Restaurant re2 = new Restaurant();
        re2.setId(2L);

        RestaurantSumVotes rs2 = new RestaurantSumVotes(re2, 1L);
        assertThat(restaurantSumVotes).containsOnly(rs1, rs2);
    }
}
