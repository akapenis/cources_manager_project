package myy803.webAppProject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import myy803.webAppProject.entities.Course;
import myy803.webAppProject.entities.Student;
import myy803.webAppProject.services.CourseService;
import myy803.webAppProject.repository.CourseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRepositoryTest {

	@MockBean
	private CourseRepository cRepo;
	
	@Autowired
	private CourseService service;
	
	List <Student> students;

	@Test
	public void saveCourseTest() {
		Course course1 = new Course((long) 1, "Python", 1, "Winter", "Simple Language", students);
		when(cRepo.save(course1)).thenReturn(course1);
		assertEquals(course1, service.addCourse(course1));
	}
	
	@Test
	public void getCourseByIdTest() {
		Course course1 = new Course((long) 1, "Python", 1, "Winter", "Simple Language", students);
		Long id = course1.getId();
		when(cRepo.findById(id)).thenReturn(Optional.of(course1));
		assertEquals(course1, service.findCourse(id));
	}
	
	@Test
	public void deleteCourseTest() {
		Course course1 = new Course((long) 1, "Python", 1, "Winter", "Simple Language", students);
		Long id = course1.getId();
		service.deleteCourse(id);
		verify(cRepo, times(1)).deleteById(course1.getId());
	}
	
	@Test
	public void getCoursesTest() {
		when(cRepo.findAll()).thenReturn(Stream.of(
			new Course((long) 1, "Python", 1, "Winter", "Simple Language",
				students),
			new Course((long) 2, "Java", 1, "Spring", "Object oriented",
				students))
			.collect(Collectors.toList()));
		assertEquals(2, service.getCourses().size());
	}
	
	@Test
	public void getCoursesbyUserIdTest() {
		Long id = (long) 7746;
		when(cRepo.findAllByUserId(id))
			.thenReturn(Stream.of
				(new Course((long) 7, "Python", 1, "Winter",
						"Simple Language", students))
					.collect(Collectors.toList()));
		assertEquals(1, service.getCoursesByUserId(id).size());
	}
	
}