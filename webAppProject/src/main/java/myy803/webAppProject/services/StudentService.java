package myy803.webAppProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.webAppProject.entities.Student;
import myy803.webAppProject.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository sRepo;
	
	public Student addStudent(Student student) {
		return sRepo.save(student);
	}
	
	public Student findStudent(Long studentId) {
		Student student = sRepo.findById(studentId).get();
		return student;
	}
	
	public void deleteStudent(Long studentId) {
		sRepo.deleteById(studentId);
	}
	
	public List<Student> getStudentsByCourseId(Long courseId){
		List<Student> students = sRepo.findAllByCourseId(courseId);
		return students;
	}

}
