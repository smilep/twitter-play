package com.smilep.twitter.service;

import com.smilep.twitter.model.FollowersResponse;

public interface FollowersService {

    FollowersResponse isXFollowsY(String handle1, String handle2);

}
