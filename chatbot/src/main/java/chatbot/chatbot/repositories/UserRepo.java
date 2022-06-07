package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
