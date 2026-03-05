package br.com.rodrigo.service;

import jakarta.transaction.Transactional;
import br.com.rodrigo.entity.Course;
import br.com.rodrigo.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.com.rodrigo.entity.Lesson;
import br.com.rodrigo.repository.LessonRepository;
import java.util.List;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository repository;

    @Inject
    LessonRepository lessonRepository;

    @Transactional
    public Course create(Course course) {
        repository.persist(course);
        return course;
    }

    public List<Course> listAll() {
        return repository.listAll();
    }

    public Course findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }

    @Transactional
    public Course update(Long id, Course updatedCourse) {

        Course course = repository.findById(id);

        if (course == null) {
            return null;
        }

        course.name = updatedCourse.name;

        return course;
    }
    @Transactional
    public Lesson addLesson(Long courseId, Lesson lesson) {

        Course course = repository.findById(courseId);

        if (course == null) {
            return null;
        }

        lesson.course = course;
        lessonRepository.persist(lesson);

        return lesson;
    }

    public List<Lesson> listLessons(Long courseId) {

        Course course = repository.findById(courseId);

        if (course == null) {
            return null;
        }

        return lessonRepository.list("course.id", courseId);
    }
}