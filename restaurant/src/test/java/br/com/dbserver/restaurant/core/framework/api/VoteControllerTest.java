package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.AbstractWebTest;
import br.com.dbserver.restaurant.core.domain.RestaurantException;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.service.VoteTimeAllowedService;
import br.com.dbserver.restaurant.core.infrastructure.config.RestaurantError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/sql/clear-database.sql", "/sql/list-restaurant.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoteControllerTest extends AbstractWebTest {
    private static final String VOTE_URL = "/vote";
    private static String authorizationToken;
    @Autowired
    private EntityManager em;

    @MockBean
    private VoteTimeAllowedService voteTimeAllowedService;

    @BeforeAll
    private void setup() {
        authorizationToken = getToken("user1", "123456");
    }

    @BeforeEach
    private void each() {
        Mockito.when(voteTimeAllowedService.isTimeAllowed()).thenReturn(true);
    }

    @Test
    void postVote() {
        final Long restaurantId = 1L;
        HttpEntity<Long> httpEntity = createAuthorizationEntity(restaurantId, authorizationToken);

        assertThat(getAllVotes()).hasSize(0);

        ResponseEntity<String> voteResult = restTemplate.postForEntity(getServerUrl(VOTE_URL),
                httpEntity,
                String.class);

        assertThat(voteResult.getStatusCode().value()).isEqualTo(200);
        List<Vote> votes = getAllVotes();
        assertThat(votes).hasSize(1);
        Vote vote = votes.get(0);
        assertThat(vote.getUser().getUsername()).isEqualTo("user1");
        assertThat(vote.getRestaurant().getId()).isEqualTo(1L);

    }

    @Test
    void oneVotePerDayPerUser() throws JsonProcessingException {
        final Long restaurantId = 1L;
        HttpEntity<Long> httpEntity = createAuthorizationEntity(restaurantId, authorizationToken);

        ResponseEntity<RestaurantError> voteResult = restTemplate.postForEntity(getServerUrl(VOTE_URL),
                httpEntity,
                RestaurantError.class);

        ResponseEntity<RestaurantError> voteResult2 = restTemplate.postForEntity(getServerUrl(VOTE_URL),
                httpEntity,
                RestaurantError.class);

        assertThat(voteResult2.getStatusCode().is4xxClientError()).isTrue();
        assertThat(voteResult2.getBody().getMessage()).isEqualTo("Vote already computed for this user today");
        assertThat(getAllVotes()).hasSize(1);
    }

    private List<Vote> getAllVotes() {
        return em.createQuery("select v from Vote v", Vote.class)
                .getResultList();
    }
}
