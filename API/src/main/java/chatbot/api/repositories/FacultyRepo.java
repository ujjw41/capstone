package chatbot.api.repositories;

import chatbot.chatbot.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepo extends JpaRepository<Faculty, Long> {
	Faculty findByUsername(String username);
	Faculty findByName(String name);

}
