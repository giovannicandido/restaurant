package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.Restaurant;

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
}
