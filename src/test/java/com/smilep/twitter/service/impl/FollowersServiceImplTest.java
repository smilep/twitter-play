package com.smilep.twitter.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smilep.twitter.model.FollowersResponse;
import com.smilep.twitter.service.FollowersService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class FollowersServiceImplTest {

    @Autowired
    private FollowersService followersService;

    @Test
    public void testIsXFollowsY() {
        FollowersResponse followersResponse = followersService.isXFollowsY("elims_smile", "gabbbarsingh");
        System.out.println(followersResponse);
    }

}
