package com.smilep.twitter.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smilep.twitter.constant.Constants;
import com.smilep.twitter.helper.TwitterOauthHeaderGenerator;
import com.smilep.twitter.model.FollowersResponse;
import com.smilep.twitter.model.FollowersResponseDTO;
import com.smilep.twitter.model.UserDTO;
import com.smilep.twitter.service.FollowersService;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FollowersServiceImpl implements FollowersService {

    @Autowired
    @Qualifier("twitterRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private TwitterOauthHeaderGenerator generator;

    @Override
    public FollowersResponse isXFollowsY(String x, String y) {
        UserDTO user = getUser(y);
        FollowersResponseDTO followersResponseDTO = getFollows(x);
        FollowersResponse followersResponse = new FollowersResponse();
        boolean isFollowing = followersResponseDTO.getIds().contains(user.getId());
        followersResponse.setIsFollowing(isFollowing);
        return followersResponse;
    }

    private UserDTO getUser(String handle) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(Constants.SCREEN_NAME, handle);
        String header = generator.generateHeader(HttpMethod.GET.name(), Constants.USERS_API, requestParams);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<String> httpEntity = new HttpEntity<String>("body", headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(Constants.USERS_API).queryParam(Constants.SCREEN_NAME, handle);
        ResponseEntity<UserDTO> userResEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, httpEntity,
                UserDTO.class);
        UserDTO userDTO = userResEntity.getBody();
        return userDTO;
    }

    private FollowersResponseDTO getFollows(String handle) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("cursor", "-1");
        requestParams.put("count", "5000");
        requestParams.put("screen_name", handle);
        String header = generator.generateHeader(HttpMethod.GET.name(), Constants.FRIENDS_IDS_API, requestParams);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<String> httpEntity = new HttpEntity<String>("body", headers);
        ResponseEntity<FollowersResponseDTO> followsResEntity = restTemplate.exchange(Constants.FRIENDS_IDS_API + "?cursor=-1&count=5000&screen_name=" + handle,
                HttpMethod.GET, httpEntity, FollowersResponseDTO.class);
        FollowersResponseDTO followersResponse = followsResEntity.getBody();
        return followersResponse;
    }

}
