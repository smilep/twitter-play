package com.smilep.twitter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class TwitterErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new RuntimeException("Oh! An ocurred connecting to Twitter! Please try with another data or try later");
        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (response.getStatusCode() == HttpStatus.NOT_FOUND || response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Seems like you entered an invalid Twitter handle!");
            } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new RuntimeException("Seems like the Twitter Handle is protected! This service works only for public Twitter handles.");
            }
        }

    }

}
