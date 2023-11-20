package org.matheuscordeiro.socialapi.rest.dto;

import java.util.List;

public record FollowersPerUserResponse(Integer followersCount, List<FollowerResponse> content) {
}
