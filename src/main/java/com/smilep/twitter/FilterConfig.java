package com.smilep.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smilep.twitter.filter.RateLimitFilter;

@Configuration
public class FilterConfig {

    @Autowired
    private ConfigProvider configProvider;

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitFilter(configProvider));
        registrationBean.addUrlPatterns("/follows/isXFollowsY/*");
        return registrationBean;
    }

}
