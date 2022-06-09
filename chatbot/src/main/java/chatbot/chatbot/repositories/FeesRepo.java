package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.Fees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeesRepo extends JpaRepository<Fees, Long> {
	Fees findByUsername(String username);

}
