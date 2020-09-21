package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.Restaurant;

@DataJpaTest
@Sql({"/sql/clear-database.sql","/sql/list-restaurant.sql"})
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository repository;

    @Test
    void findAllNotIn() {
        List<Long> ids = List.of(1L, 2L);
        Restaurant restaurant1 = repository.findById(1L).get();
        Restaurant restaurant2 = repository.findById(2L).get();
        List<Restaurant> all = repository.findAllNotIn(ids);
        assertThat(all).hasSize(2);
        assertThat(all).doesNotContain(restaurant1, restaurant2);
    }
}
