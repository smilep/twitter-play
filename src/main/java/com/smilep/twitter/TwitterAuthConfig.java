package com.smilep.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smilep.twitter.helper.TwitterOauthHeaderGenerator;

@Configuration
public class TwitterAuthConfig {

    @Value("${twitter.oauth.consumerKey}")
    private String consumerKey;

    @Value("${twitter.oauth.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.oauth.accessToken}")
    private String token;

    @Value("${twitter.oauth.accessTokenSecret}")
    private String tokenSecret;

    @Bean
    TwitterOauthHeaderGenerator twitterOauthHeaderGenerator() {
        return new TwitterOauthHeaderGenerator(consumerKey, consumerSecret, token, tokenSecret);
    }

}
