package br.com.rodrigo.resource;

import br.com.rodrigo.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public Response create(@Valid User user, @Context UriInfo uriInfo) {

        user.role = "USER"; // regra do projeto

        user.persist(); // ⭐ Panache

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(user.id.toString())
                .build();

        return Response.created(location).entity(user).build();
    }
}