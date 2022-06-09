package chatbot.api.services;

import chatbot.chatbot.entities.Chat;
import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
	@Autowired
	ChatRepo chatRepo;

	public void saveChat(Chat chat) {
		chatRepo.save(chat);
	}

	public Chat getChatByUsername(User user) {
		return chatRepo.findByUsername(user.getUsername());
	}

	public List<Chat> getAllChats() {
		return chatRepo.findAll();
	}
}
