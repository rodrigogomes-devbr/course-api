package br.com.rodrigo.resource;

import br.com.rodrigo.dto.UserDTO;
import br.com.rodrigo.entity.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public Response create(@Valid UserDTO dto, @Context UriInfo uriInfo) {

        // Verifica email duplicado
        User existingUser = User.find("email", dto.email).firstResult();
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Email already exists")
                    .build();
        }

        User user = new User();
        user.name = dto.name;
        user.email = dto.email;
        user.password = dto.password;
        user.role = "USER";

        user.persist();

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(user.id.toString())
                .build();

        return Response.created(location).entity(user).build();
    }

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/me")
    @jakarta.annotation.security.RolesAllowed({"ADMIN", "USER"})
    public Response me() {

        String email = jwt.getSubject();

        User user = User.find("email", email).firstResult();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build();
    }
}