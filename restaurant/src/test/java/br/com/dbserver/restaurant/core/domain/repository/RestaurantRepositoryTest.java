package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Temporal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.dto.RestaurantVoteDto;

@DataJpaTest
@Sql({"/sql/clear-database.sql","/sql/list-restaurant.sql","/sql/votes.sql"})
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository repository;

    @Test
    @Disabled
    void findAllNotSelectedThisWeek() {
        List<Restaurant> restaurants = repository.findAllNotSelectedThisWeek();
        assertThat(restaurants).hasSize(4);
    }
}
