package chatbot.chatbot.services;

import chatbot.chatbot.entities.Chat;
import chatbot.chatbot.entities.Conversation;
import chatbot.chatbot.entities.QnA;
import chatbot.chatbot.entities.User;
import chatbot.chatbot.repositories.ChatRepo;
import chatbot.chatbot.repositories.ConversationRepo;
import chatbot.chatbot.repositories.QnARepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ChatBotService {
	@Autowired
	ConversationRepo conversationRepo;
	@Autowired
	ChatRepo chatRepo;
	@Autowired
	QnARepo qnARepo;

//	@Autowired
//	UserService userService;

	public Conversation createConversation(String username) {
		Conversation conversation = new Conversation();
//		userService.findByUsername(username);
//		if ()
		conversation.setUsername(username);
		conversation.setStartTime(new Date());
		conversationRepo.save(conversation);
		return conversation;
	}

	public void saveChat(Long index, String user, String msg, String owner) {
		Chat message = new Chat();
		message.setMessage(msg);
		message.setUsername(user);
		message.setOwner(owner);
		message.setConversationId(index);
		message.setTime(new Date());
		chatRepo.save(message);
	}

	public String isInQnA(String message) {
		List<String> inputWords = List.of(message.split(" "));

		List<List<QnA>> categories = new ArrayList<>();

		Map<List<String>, String> dictionary = new HashMap<>();

		inputWords.forEach(word -> {
			categories.add(qnARepo.findByCategory(word));
		});

		if (categories.isEmpty()) { // || categories.get(0).isEmpty()
			List<List<QnA>> keywords = new ArrayList<>();
			inputWords.forEach(word -> {
				keywords.add(qnARepo.findByKeywords(word));
			});
			if (keywords.isEmpty() || keywords.get(0).isEmpty()) {
				log.info("couldn't find the answer for this question");
				return "Sorry, I'm Confused. Please try something else";
			} else {
				return keywords.get(0).get(0).getAnswer();
			}
		}

		categories.forEach(category -> {
			category.forEach(qnA -> {
				dictionary.put(qnA.getKeywords(), qnA.getAnswer());
			});
		});

		List<String> answers = new ArrayList<>();

		inputWords.forEach(word -> {
			dictionary.forEach((key, value) -> {
				if (key.contains(word)) {
					answers.add(value);
				}
			});
		});

		if (answers.isEmpty()) {
//			if (categories.get(0).isEmpty()){
//				return "Sorry, I'm Confused. Please try something else";
//			}
			log.info("found category but not keyword");

			return categories.get(0).get(0).getAnswer();
		} else {
			return answers.get(0);
		}
	}

	public List<Conversation> getAllConversations() {
		return conversationRepo.findAll();
	}

	public List<Chat> getChatsByConversationId(Long id) {
		return chatRepo.findAllByConversationId(id);
	}

	public void saveQnA(QnA qnA) {
		qnARepo.save(qnA);
	}

	public List<QnA> getAllQnA() {
		return qnARepo.findAll();
	}

	public void deleteQnA(QnA qnA) {
		if (qnARepo.existsById(qnA.getId())) {
			qnARepo.delete(qnA);
		} else {
			log.info("qna wasnt found");
		}
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

	public void updateQnA(QnA qnA) {
		if (qnARepo.existsById(qnA.getId())) {
			qnARepo.save(qnA);
		} else {
			log.info("qna wasnt found");
		}
	}
}
