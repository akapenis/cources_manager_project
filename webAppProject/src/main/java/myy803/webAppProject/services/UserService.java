package myy803.webAppProject.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import myy803.webAppProject.dto.UserRegistrationDto;
import myy803.webAppProject.entities.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
