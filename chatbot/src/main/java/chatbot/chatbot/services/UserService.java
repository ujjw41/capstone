package chatbot.chatbot.services;

import chatbot.chatbot.entities.Role;
import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.RoleRepo;
import chatbot.chatbot.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;


	public void saveUser(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	public void saveRole(Role role) {
		roleRepo.save(role);
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}


}
