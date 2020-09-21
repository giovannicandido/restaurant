package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.AbstractWebTest;
import br.com.dbserver.restaurant.core.application.dto.RestaurantListDto;
import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/sql/clear-database.sql", "/sql/list-restaurant.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestaurantControllerTest extends AbstractWebTest {
    private static final String LIST_URL = "/restaurant";
    private static String authorizationToken;
    @Autowired
    private VoteResultRepository voteResultRepository;

    @BeforeAll
    private void setup() {
        authorizationToken = getToken("user1", "123456");
    }

    @Test
    @Order(1)
    void listRestaurants() throws JsonProcessingException {
        HttpEntity<String> httpEntity = createAuthorizationEntity(null, authorizationToken);
        ResponseEntity<RestaurantListDto[]> response = restTemplate.exchange(getServerUrl(LIST_URL),
                HttpMethod.GET, httpEntity, RestaurantListDto[].class);
        RestaurantListDto[] responseList = response.getBody();
        assertThat(responseList).hasSize(4);
    }

    @Test
    @Order(2)
    void listAllRestaurantExceptVotedInThisWeek() {
        VoteResult voteResult = new VoteResult();
        voteResult.setDayOfResult(LocalDate.now());
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        voteResult.setWinRestaurant(restaurant);
        voteResultRepository.save(voteResult);

        HttpEntity<String> httpEntity = createAuthorizationEntity(null, authorizationToken);
        ResponseEntity<RestaurantListDto[]> response = restTemplate.exchange(getServerUrl(LIST_URL),
                HttpMethod.GET, httpEntity, RestaurantListDto[].class);
        RestaurantListDto[] responseList = response.getBody();
        assertThat(responseList).hasSize(3);

    }
}
