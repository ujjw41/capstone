package chatbot.chatbot.repositories;

import chatbot.chatbot.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
	List<Subject> findAllByUsername(String username);
}
