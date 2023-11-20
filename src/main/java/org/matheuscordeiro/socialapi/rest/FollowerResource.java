package org.matheuscordeiro.socialapi.rest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.model.Follower;
import org.matheuscordeiro.socialapi.domain.repository.FollowerRepository;
import org.matheuscordeiro.socialapi.domain.repository.UserRepository;
import org.matheuscordeiro.socialapi.rest.dto.FollowerRequest;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class FollowerResource {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @PUT
    @Transactional
    public Response followUser(
            @PathParam("userId") Long userId, FollowerRequest request) {
        if(userId.equals(request.followerId())){
            return Response.status(Response.Status.CONFLICT)
                    .entity("You can't follow yourself")
                    .build();
        }
        final var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final var follower = userRepository.findById(request.followerId());
        boolean follows = followerRepository.follows(follower, user);
        if(!follows){
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);
            followerRepository.persist(entity);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
