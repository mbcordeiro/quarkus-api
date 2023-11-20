package org.matheuscordeiro.socialapi.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.service.UserService;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;
import jakarta.validation.Validator;
import org.matheuscordeiro.socialapi.rest.dto.ResponseError;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private Validator validator;

    @POST
    public Response create(CreateUserRequest createUserRequest) {
        final var violations = validator.validate(createUserRequest);
        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
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
