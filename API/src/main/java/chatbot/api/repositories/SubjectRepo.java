package chatbot.api.repositories;

import chatbot.api.enitites.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
	List<Subject> findAllByUsername(String username);

	Subject findByName(String name);
}
