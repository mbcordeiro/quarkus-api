package org.matheuscordeiro.socialapi.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.service.UserService;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @POST
    public Response create(CreateUserRequest createUserRequest) {
        userService.create(createUserRequest);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @GET
    public Response list() {
        return Response.ok(userService.list()).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, CreateUserRequest createUserRequest) {
        final var user = userService.update(id, createUserRequest);
        if (user != null) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        final var isDeleted = userService.delete(id);
        if (isDeleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
