package com.smilep.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TwitterPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterPlayApplication.class, args);
    }

    @Bean("twitterRestTemplate")
    RestTemplate restTemplate() {
        return new RestTemplateBuilder().errorHandler(new TwitterErrorHandler()).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smilep.twitter.controller"))
                .build()
                .apiInfo(apiInfo());
    }
    
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Twitter Play")
                .description("Developed with ❤️ by Smile")
                .contact(new Contact("Smile", "", "smile.is.my.name@gmail.com"))
                .build();
    }

}
