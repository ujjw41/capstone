package chatbot.api.repositories;

import chatbot.api.enitites.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectsRepo extends JpaRepository<Subjects, Long> {
}
