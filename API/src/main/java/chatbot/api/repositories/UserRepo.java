package chatbot.api.repositories;

import chatbot.api.enitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
