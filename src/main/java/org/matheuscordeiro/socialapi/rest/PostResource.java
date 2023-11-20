package org.matheuscordeiro.socialapi.rest;

import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.model.Post;
import org.matheuscordeiro.socialapi.domain.repository.FollowerRepository;
import org.matheuscordeiro.socialapi.domain.repository.PostRepository;
import org.matheuscordeiro.socialapi.domain.repository.UserRepository;
import org.matheuscordeiro.socialapi.rest.dto.CreatePostRequest;
import org.matheuscordeiro.socialapi.rest.dto.PostResponse;

import java.util.stream.Collectors;

@Path("/users/{usersId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class PostResource {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private FollowerRepository followerRepository;

    @POST
    @Transactional
    public Response create(@PathParam("userId") Long userId, CreatePostRequest request) {
        final var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final var post = new Post();
        post.setText(request.text());
        post.setUser(user);
        postRepository.persist(post);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @GET
    public Response list(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        final var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (followerId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You forgot the header followerId").build();
        }
        final var follower = userRepository.findById(followerId);
        if (follower == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Nonexistent followerId").build();
        }
        if (!followerRepository.follows(follower, user)) {
            return Response.status(Response.Status.FORBIDDEN).entity("You can't see these posts").build();
        }
        final var query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending), user);
        final var list = query.list();
        final var postResponseList = list.stream().map(PostResponse::fromEntity).collect(Collectors.toList());
        return Response.ok(postResponseList).build();
    }
}
