package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepo extends JpaRepository<QnA, Long> {
}
