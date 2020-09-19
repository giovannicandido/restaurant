package br.com.dbserver.restaurant.core.framework.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.AbstractWebTest;
import br.com.dbserver.restaurant.core.application.dto.RestaurantListDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/sql/clear-database.sql", "/sql/list-restaurant.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantControllerTest extends AbstractWebTest {
    private static final String LIST_URL = "/restaurant";
    private ObjectMapper objectMapper = new ObjectMapper();
    private static String authorizationToken;
    @BeforeAll
    private void setup() {
        authorizationToken = getToken("user1", "123456");
    }

    @Test
    void listRestaurants() throws JsonProcessingException {
        HttpEntity<String> httpEntity = createAuthorizationEntity(null, authorizationToken);
        ResponseEntity<String> response = restTemplate.exchange(getServerUrl(LIST_URL), HttpMethod.GET, httpEntity, String.class);
        List<RestaurantListDto> responseList = objectMapper.readValue(response.getBody(), new TypeReference<List<RestaurantListDto>>() {
        });
        assertThat(responseList).hasSize(4);
    }
}
