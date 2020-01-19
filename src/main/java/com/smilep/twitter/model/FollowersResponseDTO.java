package com.smilep.twitter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowersResponseDTO {

    @JsonProperty("ids")
    private List<String> ids = null;
    @JsonProperty("next_cursor")
    private Integer nextCursor;
    @JsonProperty("next_cursor_str")
    private String nextCursorStr;
    @JsonProperty("previous_cursor")
    private Integer previousCursor;
    @JsonProperty("previous_cursor_str")
    private String previousCursorStr;
    @JsonProperty("total_count")
    private Object totalCount;

}