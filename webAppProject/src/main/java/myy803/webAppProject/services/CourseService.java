package myy803.webAppProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.webAppProject.entities.Course;
import myy803.webAppProject.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository cRepo;
	
	public Course addCourse(Course course) {
		return cRepo.save(course);
	}
	
	public Course findCourse(Long courseId) {
		Course course = cRepo.findById(courseId).get();
		return course;
	}
	
	public void deleteCourse(Long courseId) {
		cRepo.deleteById(courseId);
	}
	
	public List<Course> getCourses(){
		List<Course> courses = cRepo.findAll();
		return courses;
	}
	
	public List<Course> getCoursesByUserId(Long userId){
		List<Course> courses = cRepo.findAllByUserId(userId);
		return courses;
	}

}
