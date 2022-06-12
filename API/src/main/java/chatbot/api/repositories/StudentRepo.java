package chatbot.api.repositories;

import chatbot.api.enitites.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Long> {
	Student findByUsername(String username);

	@Override
	List<Student> findAll();
}
