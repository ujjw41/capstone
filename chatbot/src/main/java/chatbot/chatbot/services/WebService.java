package chatbot.chatbot.services;

import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WebService {
	@Autowired
	ChatRepo chatRepo;

	@Autowired
	ConversationRepo conversationRepo;

	@Autowired
	QnARepo qnARepo;

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	UserRepo userRepo;

	public void saveUser(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}
}
