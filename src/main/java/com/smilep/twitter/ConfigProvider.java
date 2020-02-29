package com.smilep.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ConfigProvider {

    @Value("${XFollowsY.rateLimit}")
    private int rateLimit;

    @Value("${XFollowsY.timeLimit}")
    private int timeLimit;

}
