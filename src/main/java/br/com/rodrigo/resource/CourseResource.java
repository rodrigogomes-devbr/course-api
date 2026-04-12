package br.com.rodrigo.resource;

import br.com.rodrigo.dto.CourseDTO;
import br.com.rodrigo.dto.LessonDTO;
import br.com.rodrigo.entity.Course;
import br.com.rodrigo.service.CourseService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import br.com.rodrigo.entity.Lesson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Context;


@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject
    CourseService service;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid CourseDTO courseDTO, @Context UriInfo uriInfo) {

        Course course = new Course();
        course.name = courseDTO.name;

        Course saved = service.create(course);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(saved.id.toString())
                .build();

        return Response.created(location).entity(saved).build();
    }

    @GET
    @PermitAll
    public List<Course> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response find(@PathParam("id") Long id) {

        Course course = service.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(course).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid CourseDTO courseDTO) {

        Course updatedCourse = new Course();
        updatedCourse.name = courseDTO.name;

        Course course = service.update(id, updatedCourse);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(course).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {

        boolean deleted = service.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }
    @POST
    @Path("/{courseId}/lessons")
    @RolesAllowed("ADMIN")
    public Response addLesson(@PathParam("courseId") Long courseId,
                              @Valid LessonDTO lessonDTO) {

        Lesson lesson = new Lesson();
        lesson.name = lessonDTO.name;

        Lesson saved = service.addLesson(courseId, lesson);

        if (saved == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Path("/{courseId}/lessons")
    @PermitAll
    public Response listLessons(@PathParam("courseId") Long courseId) {

        List<Lesson> lessons = service.listLessons(courseId);

        if (lessons == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(lessons).build();
    }
    @GET
    @Path("/debug-auth")
    @PermitAll
    public Response debugAuth(@Context SecurityContext ctx) {
        return Response.ok(
                "User: " + ctx.getUserPrincipal() +
                        " | Auth: " + ctx.isUserInRole("ADMIN")
        ).build();
    }
}
