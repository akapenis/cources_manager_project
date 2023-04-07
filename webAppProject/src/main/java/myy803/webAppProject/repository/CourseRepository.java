package myy803.webAppProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.webAppProject.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	@SuppressWarnings("unchecked")
	Course save(Course course);
	Course findById(long id);
	Course deleteById(long id);
	List<Course> findAll();
	List<Course> findAllByUserId(long id);
}
