package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

	String faculty;

	Date dob;

	boolean feesStatus;

	String fees;

	String Subjects;
}
