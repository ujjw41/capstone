package chatbot.chatbot.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTemplate {
	Long studentId;
	Long libraryCardNumber;
	String name;
	String username;
	String faculty;
//	String[] subjects;
}
