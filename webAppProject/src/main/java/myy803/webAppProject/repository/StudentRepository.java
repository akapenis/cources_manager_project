package myy803.webAppProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.webAppProject.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	@SuppressWarnings("unchecked")
	Student save(Student student);
	Student findById(long id);
	Student deleteById(long id);
	List<Student> findAllByCourseId(long id);
}
