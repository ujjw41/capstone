package chatbot.api.services;

import chatbot.api.enitites.Chat;
import chatbot.api.enitites.Conversation;
import chatbot.api.enitites.QnA;
import chatbot.api.enitites.User;
import chatbot.api.repositories.ChatRepo;
import chatbot.api.repositories.ConversationRepo;
import chatbot.api.repositories.QnARepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public String mostOccurringString(List<String> answers) {
		if (answers.isEmpty()) {
			return "Sorry, could not find an answer";
		}
		Map<String, Integer> frequency = new HashMap<>();
		answers.forEach(answer -> {
			if (frequency.containsKey(answer)) {
				frequency.put(answer, frequency.get(answer) + 1);
			} else {
				frequency.put(answer, 1);
			}
		});
		Map.Entry<String, Integer> maxEntry = null;
		for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			}
		}
		return maxEntry.getKey();
	}

	public String isInQnA(String message) {
		String pattern = "(?U)\\w+|\\W+";
		List<String> inputWords = new ArrayList<>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(message);
		while (m.find()) {
			inputWords.add(m.group(0));
		}

		List<List<QnA>> categories = new ArrayList<>();
		inputWords.forEach(word -> {
			List<QnA> temp = qnARepo.findByCategory(word);
			if (!temp.isEmpty()) {
				categories.add(temp);
			}
		});

		if (categories.isEmpty()) { //|| categories.get(0).isEmpty()
			List<List<QnA>> keywords = new ArrayList<>();
			inputWords.forEach(word -> {
				List<QnA> temp = qnARepo.findByKeywords(word);
				if (!temp.isEmpty()) {
					keywords.add(temp);
				}
			});
			if (keywords.isEmpty()) { // || keywords.get(0).isEmpty()
				log.info("couldn't find the answer for this question");
				return "Sorry, I'm Confused. Please try something else";
			} else {
				List<String> answerList = new ArrayList<>();
				keywords.forEach(keyword -> {
					keyword.forEach(qnA -> {
						answerList.add(qnA.getAnswer());
					});
				});
				return this.mostOccurringString(answerList);
			}
		}

		Map<List<String>, String> dictionary = new HashMap<>();
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
//			if (categories.get(0).isEmpty()) {
//				return "Sorry two, I'm Confused. Please try something else";
//			}
			log.info("found category but not keyword");
			List<String> answerList = new ArrayList<>();
			dictionary.forEach((key, value) ->{
				answerList.add(value);
			});
			return this.mostOccurringString(answerList);
		} else {
			return this.mostOccurringString(answers);
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
