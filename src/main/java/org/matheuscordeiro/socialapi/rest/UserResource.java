package org.matheuscordeiro.socialapi.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.matheuscordeiro.socialapi.rest.dto.CreateUserRequest;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @POST
    public Response create(CreateUserRequest createUserRequest) {
        return Response.ok(createUserRequest).build();
    }

    @GET
    public Response list() {
        return Response.ok().build();
    }
}
