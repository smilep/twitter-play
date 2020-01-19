package com.smilep.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smilep.twitter.model.FollowersResponse;
import com.smilep.twitter.service.FollowersService;

@RestController
@RequestMapping(path = "/follows")
public class FollowsController {

    @Autowired
    private FollowersService followersService;

    @GetMapping
    public FollowersResponse isXFollowsY(@RequestParam("x") String x, @RequestParam("y") String y) {
        return followersService.isXFollowsY(x, y);
    }

}
