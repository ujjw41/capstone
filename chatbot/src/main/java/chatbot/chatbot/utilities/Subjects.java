package chatbot.chatbot.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subjects {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;
	Long grade;
	boolean status;
}
