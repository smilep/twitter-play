package com.smilep.twitter;

import com.smilep.twitter.helper.TwitterOauthHeaderGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("To be run manually")
@SpringBootTest
public class PlayTest {

    @Autowired
    @Qualifier("twitterRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private TwitterOauthHeaderGenerator generator;

    @Test
    public void getTweet() {
        Map<String, String> requestParams = new HashMap<>();
        String id = "1263213348000325633";
        requestParams.put("id", id);
        String url = "https://api.twitter.com/1.1/statuses/lookup.json";
        String header = generator.generateHeader(HttpMethod.GET.name(), url, requestParams);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<String> httpEntity = new HttpEntity<String>("body", headers);
        // String concatenation is not good for passing in GET params. Use better approach.
        ResponseEntity<String> response = restTemplate.exchange(url + "?id=" + id, HttpMethod.GET, httpEntity,
                String.class);
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        System.out.println(responseBody);
    }
}
