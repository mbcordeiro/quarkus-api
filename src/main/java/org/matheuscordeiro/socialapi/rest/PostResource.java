package org.matheuscordeiro.socialapi.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/users/{usersId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class PostResource {
    @POST
    public Response create() {
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @GET
    public Response list() {
        return Response.ok().build();
    }
}
