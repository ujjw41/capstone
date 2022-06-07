package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
}
