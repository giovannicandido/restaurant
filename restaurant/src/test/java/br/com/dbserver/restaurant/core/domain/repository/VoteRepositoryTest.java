package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.Restaurant;
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
}
