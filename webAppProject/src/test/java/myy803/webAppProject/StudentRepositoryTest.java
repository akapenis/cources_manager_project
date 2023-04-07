package myy803.webAppProject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import myy803.webAppProject.repository.StudentRepository;
import myy803.webAppProject.services.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {

	@MockBean
	private StudentRepository sRepo;

	@Autowired
	private StudentService service;

	@Test
	public void saveStudentTest() {
		Student student1 = new Student(
			(long) 1, "John", "Georgioy", 2018, 8, 0, 0, new Course());
		when(sRepo.save(student1)).thenReturn(student1);
		assertEquals(student1, service.addStudent(student1));
	}

	@Test
	public void getStudentByIdTest() {
		Student student1 = new Student(
			(long) 1, "John", "Georgioy", 2018, 8, 0, 0, new Course());
		Long id = student1.getId();
		when(sRepo.findById(id)).thenReturn(Optional.of(student1));
		assertEquals(student1, service.findStudent(id));
	}

	@Test
	public void deleteStudentTest() {
		Student student1 = new Student(
			(long) 1, "John", "Georgioy", 2018, 8, 0, 0, new Course());
		Long id = student1.getId();
		service.deleteStudent(id);
		verify(sRepo, times(1)).deleteById(student1.getId());
	}

	@Test
	public void getStudentsbyCourseIdTest() {
		Long id = (long) 7746;
		when(sRepo.findAllByCourseId(id)).thenReturn(Stream.of(
			new Student(
				(long) 1, "John", "Georgioy", 2018, 8, 0, 0, new Course()),
			new Student(
				(long) 2, "Nick", "Dimitrioy", 2016, 12, 0, 0, new Course()))
			.collect(Collectors.toList()));
		assertEquals(2, service.getStudentsByCourseId(id).size());
	}

}
