package br.com.rodrigo.resource;

import br.com.rodrigo.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    public static class AuthRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public long expiresIn;

        public AuthResponse(String token, long expiresIn) {
            this.token = token;
            this.expiresIn = expiresIn;
        }
    }

    @POST
    @Path("/token")
    public Response login(AuthRequest request) {

        User user = User.find("email", request.email).firstResult();

        if (user == null || !user.password.equals(request.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Set<String> roles = new HashSet<>();
        roles.add(user.role);

        String token = Jwt.issuer("course-api")
                .subject(user.email)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .signWithSecret("minha-chave-super-segura-com-mais-de-32-caracteres-123");

        return Response.ok(new AuthResponse(token, 3600)).build();
    }
    @GET
    @Path("/debug-users")
    public Response debug() {
        return Response.ok(User.listAll()).build();
    }
}