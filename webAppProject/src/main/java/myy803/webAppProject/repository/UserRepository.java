package myy803.webAppProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.webAppProject.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findById(long id);
	User findUserByUsername(String username);
	User findByCourseId(long id);
}

