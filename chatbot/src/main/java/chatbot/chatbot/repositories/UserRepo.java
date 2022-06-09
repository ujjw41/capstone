package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByUsername(String username);

	@Override
	List<User> findAll();
}
