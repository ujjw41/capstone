package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepo extends JpaRepository<QnA, Long> {
	@Override
	List<QnA> findAll();
	List<QnA> findAllByQuestion(String message);
}
