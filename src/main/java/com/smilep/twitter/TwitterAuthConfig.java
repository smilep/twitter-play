package com.smilep.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.smilep.twitter.helper.TwitterOauthHeaderGenerator;

@Configuration
@PropertySource("classpath:twitter.properties")
public class TwitterAuthConfig {

    @Value("${oauth.consumerKey}")
    private String consumerKey;

    @Value("${oauth.consumerSecret}")
    private String consumerSecret;

    @Value("${oauth.accessToken}")
    private String token;

    @Value("${oauth.accessTokenSecret}")
    private String tokenSecret;

    @Bean
    TwitterOauthHeaderGenerator twitterOauthHeaderGenerator() {
        return new TwitterOauthHeaderGenerator(consumerKey, consumerSecret, token, tokenSecret);
    }

}
