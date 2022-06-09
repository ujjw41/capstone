package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
	@Override
	List<Conversation> findAll();
}
