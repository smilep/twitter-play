package com.smilep.twitter.filter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;

import com.smilep.twitter.ConfigProvider;

/**
 * Basic Rate Limit filter. Works only for single node deployment.
 *
 */
@Order(1)
public class RateLimitFilter implements Filter {

    private static Instant startTime = Instant.now();
    private static int count = 0;
    private ConfigProvider configProvider;

    public RateLimitFilter(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Duration.between(startTime, Instant.now()).getSeconds() > configProvider.getTimeLimit()) {
            startTime = Instant.now();
            count = 0;
        }
        if (count >= configProvider.getRateLimit()) {
            throw new RuntimeException("Rate limit reached! Slow down!");
        }
        count++;
        chain.doFilter(request, response);
    }

}
