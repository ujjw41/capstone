package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	Long studentId;
	Long libraryCardNumber;
	String name;
	String username;

	@ManyToOne
	Faculty faculty;

	@OneToOne
	Fees fees;

	@ManyToMany
	List<Subject> Subject;
}
