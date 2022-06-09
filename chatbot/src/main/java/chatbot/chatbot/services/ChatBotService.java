package chatbot.chatbot.services;

import chatbot.chatbot.entities.Chat;
import chatbot.chatbot.entities.Conversation;
import chatbot.chatbot.entities.QnA;
import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.ChatRepo;
import chatbot.chatbot.repositories.ConversationRepo;
import chatbot.chatbot.repositories.QnARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatBotService {
	@Autowired
	ConversationRepo conversationRepo;
	@Autowired
	ChatRepo chatRepo;
	@Autowired
	QnARepo qnARepo;

	public Conversation createConversation(String anonymous) {
		Conversation conversation = new Conversation();
		conversation.setUsername("Anonymous");
		conversation.setStartTime(new Date());
		conversationRepo.save(conversation);
		return conversation;
	}

	public void saveChat(Long index, String user, String msg, String owner) {
		Chat message = new Chat();
		message.setMessage(msg);
		message.setUsername(user);
		message.setOwner(owner);
		message.setConversation(index);
		message.setTime(new Date());
		chatRepo.save(message);
	}

	public String isInQnA(String message) {

		List<QnA> qnAList = qnARepo.findAllByQuestion(message);

		if (qnAList.isEmpty()) {
			return "Sorry! Couldnt help this moment. Try something else....";
		} else {
			return qnAList.get(0).getAnswer();
		}

	}

	public List<Conversation> getConversations() {
		return conversationRepo.findAll();
	}

	public List<Chat> getChats(Long id) {
		return chatRepo.findAllByConversation(id);
	}

	public void saveQnA(QnA qnA) {
		qnARepo.save(qnA);
	}

	public List<QnA> getAllQnA() {
		return qnARepo.findAll();
	}

	public List<Conversation> getAllConversations() {
		return conversationRepo.findAll();
	}

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
