package org.matheuscordeiro.socialapi.rest;

import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.matheuscordeiro.socialapi.domain.model.User;
import org.matheuscordeiro.socialapi.domain.repository.UserRepository;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;
import org.matheuscordeiro.socialapi.rest.dto.ResponseError;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {
    private final UserRepository userRepository;

    private Validator validator;

    @POST
    @Transactional
    public Response create(CreateUserRequest userRequest) {
        final var violations = validator.validate(userRequest);
        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }
        final var user = new User();
        user.setAge(userRequest.age());
        user.setName(userRequest.name());
        userRepository.persist(user);
        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    public Response list() {
        return Response.ok(userRepository.findAll()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, CreateUserRequest userData) {
        final var user = userRepository.findById(id);
        if(user != null){
            user.setName(userData.name());
            user.setAge(userData.age());
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        final var user = userRepository.findById(id);
        if(user != null){
            userRepository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
