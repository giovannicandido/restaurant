package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.AbstractWebTest;
import br.com.dbserver.restaurant.core.application.dto.VoteResultListDto;
import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/sql/clear-database.sql", "/sql/list-restaurant.sql", "/sql/vote_result.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoteResultControllerTest extends AbstractWebTest {
    private static final String VOTE_RESULT_URL = "vote-result";
    @Autowired
    private VoteResultRepository voteResultRepository;
    private String authorizationToken;

    @BeforeAll
    private void setup() {
        authorizationToken = getToken("user1", "123456");
    }

    @Test
    void getAllThisWeek() {
        createVoteResults();
        HttpEntity<Long> httpEntity = createAuthorizationEntity(null, authorizationToken);

        ResponseEntity<VoteResultListDto[]> voteResult = restTemplate.exchange(
                getServerUrl(VOTE_RESULT_URL),
                HttpMethod.GET,
                httpEntity,
                VoteResultListDto[].class);

        assertThat(voteResult.getStatusCode().value()).isEqualTo(200);
        assertThat(voteResult.getBody()).hasSize(1);
    }

    private void createVoteResults() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        VoteResult v1 = new VoteResult();
        v1.setWinRestaurant(restaurant);
        v1.setNumberOfVotes(1L);
        v1.setDayOfResult(LocalDate.now());
        VoteResult v2 = new VoteResult();
        v2.setId(2L);
        v2.setWinRestaurant(restaurant);
        v2.setNumberOfVotes(1L);
        v2.setDayOfResult(LocalDate.now().minusWeeks(2));
        voteResultRepository.save(v1);
        voteResultRepository.save(v2);
    }
}
