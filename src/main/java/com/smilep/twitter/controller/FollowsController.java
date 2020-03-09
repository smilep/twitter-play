package com.smilep.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smilep.twitter.model.FollowersResponse;
import com.smilep.twitter.service.FollowersService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin
@RequestMapping(path = "/follows")
public class FollowsController {

    @Autowired
    private FollowersService followersService;

    @GetMapping(path = "/isXFollowsY")
    @ApiOperation(value = "Is handle X following handle Y?")
    public FollowersResponse isXFollowsY(@RequestParam("x") @ApiParam(required = true, value = "Twitter Handle X") String x,
            @RequestParam("y") @ApiParam(required = true, value = "Twitter Handle Y") String y) {
        x = x.charAt(0) == '@' ? x.replaceFirst("@", "") : x;
        y = y.charAt(0) == '@' ? y.replaceFirst("@", "") : y;
        return followersService.isXFollowsY(x, y);
    }

}
