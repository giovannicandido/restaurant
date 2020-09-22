package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;
import br.com.dbserver.restaurant.core.domain.service.VoteTimeAllowedService;
import br.com.dbserver.restaurant.security.domain.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/sql/clear-database.sql", "/sql/list-restaurant.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestaurantControllerTest extends AbstractWebTest {
    private static final String LIST_URL = "/restaurant";
    private static String authorizationToken;
    @Autowired
    private VoteResultRepository voteResultRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteTimeAllowedService voteTimeAllowedService;

    @BeforeAll
    private void setup() {
        authorizationToken = getToken("user1", "123456");
    }

    @Test
    @Order(1)
    void listRestaurants() {
        create1VotesForId1();
        HttpEntity<String> httpEntity = createAuthorizationEntity(null, authorizationToken);
        ResponseEntity<RestaurantListDto[]> response = restTemplate.exchange(getServerUrl(LIST_URL),
                HttpMethod.GET, httpEntity, RestaurantListDto[].class);
        List<RestaurantListDto> responseList = Arrays.asList(response.getBody());

        assertThat(responseList).hasSize(4);

        Optional<RestaurantListDto> restaurantId1 = responseList
                .stream()
                .filter(r -> r.getId() == 1)
                .findFirst();

        assertThat(restaurantId1.get().getNumberOfVotes()).isEqualTo(1);
    }

    private void create1VotesForId1() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        Vote vote = new Vote();
        vote.setRestaurant(restaurant);
        vote.setDateTime(voteTimeAllowedService.getStartTime().plusSeconds(1));
        User user = new User();
        user.setId(1L);
        vote.setUser(user);
        voteRepository.save(vote);
    }

    @Test
    @Order(2)
    void listAllRestaurantExceptVotedInThisWeek() {
        VoteResult voteResult = new VoteResult();
        voteResult.setDayOfResult(LocalDate.now());
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        voteResult.setWinRestaurant(restaurant);
        voteResult.setNumberOfVotes(2L);
        voteResultRepository.save(voteResult);

        HttpEntity<String> httpEntity = createAuthorizationEntity(null, authorizationToken);
        ResponseEntity<RestaurantListDto[]> response = restTemplate.exchange(getServerUrl(LIST_URL),
                HttpMethod.GET, httpEntity, RestaurantListDto[].class);
        RestaurantListDto[] responseList = response.getBody();
        assertThat(responseList).hasSize(3);

    }
}
