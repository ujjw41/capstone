package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Long> {
}
