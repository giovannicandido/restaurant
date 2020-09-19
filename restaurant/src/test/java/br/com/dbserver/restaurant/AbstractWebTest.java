package br.com.dbserver.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.dbserver.restaurant.security.framework.api.request.LoginRequest;
import br.com.dbserver.restaurant.security.framework.api.response.JwtResponse;

public abstract class AbstractWebTest {

    @LocalServerPort
    protected int port;
    @Value("${v1Api}")
    protected String v1Api;

    @Autowired
    protected TestRestTemplate restTemplate;


    protected String getServerUrl(String url) {
        return "http://localhost:" + port + "/" + v1Api +
                "/" + url;
    }

    protected <T> List<T> exchangeForObjectList(final String path, final HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<List<T>> response = restTemplate.exchange(
                path,
                method,
                request,
                new ParameterizedTypeReference<List<T>>(){});
        List<T> list = response.getBody();
        return list;
    }

    protected String getToken(String user, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user);
        loginRequest.setPassword(password);
        ResponseEntity<JwtResponse> jwtResponseResponseEntity = restTemplate
                .postForEntity(getServerUrl("/auth/signin"), loginRequest, JwtResponse.class);

        return jwtResponseResponseEntity.getBody().getAccessToken();
    }

    protected <T> HttpEntity<T> createAuthorizationEntity(T body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new HttpEntity<>(body, headers);
    }

}
