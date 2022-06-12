package chatbot.api.repositories;

import chatbot.api.enitites.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Long> {
	Chat findByUsername(String username);

	@Override
	List<Chat> findAll();

	List<Chat> findAllByConversationId(Long id);


}
