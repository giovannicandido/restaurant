package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.AbstractWebTest;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.service.VoteTimeAllowedService;
import br.com.dbserver.restaurant.core.infrastructure.config.RestaurantError;
import com.fasterxml.jackson.core.JsonProcessingException;

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

    @Test
    void timeNotAllowed() {
        LocalDateTime startDate = LocalDateTime.of(2019, 9, 19, 0, 0);
        LocalDateTime finishedDate = LocalDateTime.of(2019, 9, 19, 12, 0);
        Mockito.when(voteTimeAllowedService.isTimeAllowed()).thenReturn(false);
        Mockito.when(voteTimeAllowedService.getStartTime()).thenReturn(startDate);
        Mockito.when(voteTimeAllowedService.getFinishTime()).thenReturn(finishedDate);

        HttpEntity<Long> httpEntity = createAuthorizationEntity(1L, authorizationToken);

        ResponseEntity<RestaurantError> voteResult = restTemplate.postForEntity(getServerUrl(VOTE_URL),
                httpEntity,
                RestaurantError.class);

        assertThat(voteResult.getStatusCode().is4xxClientError()).isTrue();
        assertThat(voteResult.getBody().getMessage())
                .isEqualTo("You cannot vote at this time. you can vote between " +
                        "19/09/2019 00:00:00 and 19/09/2019 12:00:00");
    }
}
