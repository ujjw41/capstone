package chatbot.api.services;

import chatbot.chatbot.entities.Conversation;
import chatbot.chatbot.entities.Role;
import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.ConversationRepo;
import chatbot.chatbot.repositories.RoleRepo;
import chatbot.chatbot.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {//implements UserDetailsService
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	ConversationRepo conversationRepo;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepo.findByUsername(username);
//		if (user == null) {
//			log.error("user not found in database");
//			throw new UsernameNotFoundException("User not found");
//		} else {
//			log.info("user found ind db: {}", username);
//		}
//
//		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		user.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//	}

	public void saveUser(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	public void saveRole(Role role) {
		roleRepo.save(role);
	}

//	public void addRoleToUser(String username, String roleName) {
//		User user = userRepo.findByUsername(username);
//		if (user == null) {
//			return;
//		}
//		Role role = roleRepo.findByName(roleName);
//		if (role == null) {
//			return;
//		}
//		user.getRoles().add(role);
//		userRepo.save(user);
//	}

	public Conversation createConversation(String anonymous) {
		Conversation conversation = new Conversation();
		conversation.setUsername("Anonymous");
		conversation.setStartTime(new Date());
		conversationRepo.save(conversation);
		return conversation;
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public List<Conversation> getAllConversations() {
		return conversationRepo.findAll();
	}

}