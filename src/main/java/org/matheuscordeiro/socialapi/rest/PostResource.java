package org.matheuscordeiro.socialapi.rest;

import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.model.Post;
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
    public Response list(@PathParam("userId") Long userId) {
        final var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final var list = postRepository.find("user", Sort.by("creation_date",
                Sort.Direction.Descending), user);
        var postResponseList = list.stream().map(PostResponse::fromEntity).collect(Collectors.toList());
        return Response.ok(postResponseList).build();
    }
}
