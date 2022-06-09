package chatbot.api.repositories;

import chatbot.chatbot.entities.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepo extends JpaRepository<QnA, Long> {
	@Override
	List<QnA> findAll();
}
