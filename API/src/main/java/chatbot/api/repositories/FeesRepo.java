package chatbot.api.repositories;

import chatbot.api.enitites.Fees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeesRepo extends JpaRepository<Fees, Long> {
}
