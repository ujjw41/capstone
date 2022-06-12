package chatbot.chatbot;

import chatbot.chatbot.entities.*;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}


	@Bean
	CommandLineRunner run(UserService userService, StudentService studentService, ChatBotService chatBotService) {
		return args -> {
			try {
				userService.saveUser(new User(null, "ujjwal", "ujjwal@g.in", "ujjwal", "ujjwal", "ROLE_ADMIN", true));
				userService.saveUser(new User(null, "sattyda", "sattyda@g.in", "sattyda", "sattyda", "ROLE_USER", true));
				userService.saveUser(new User(null, "test", "test@g.in", "test", "test", "ROLE_STUDENT", true));
				userService.saveUser(new User(null, "tester", "tester@g.in", "tester", "tester", "ROLE_TEACHER", true));

				chatBotService.saveQnA(new QnA(null, "Hello, How may I be of service?", List.of("hello", "hi", "whatsup"), "greeting"));
				chatBotService.saveQnA(new QnA(null, "I loved talking with you, looking forward to seeing you again", List.of("bye", "end"), "greeting"));
				chatBotService.saveQnA(new QnA(null, "name of the university is Vistula", List.of("name", "university"), "campus"));
				chatBotService.saveQnA(new QnA(null, "size of the campus is 1000 sq.km.", List.of("size", "km"), "campus"));
				chatBotService.saveQnA(new QnA(null, "color of the university is best type of blue", List.of("color", "blue"), "campus"));
				chatBotService.saveQnA(new QnA(null, "There are currently 15 departments", List.of("number"), "department"));
				chatBotService.saveQnA(new QnA(null, "current ratio of male:female is 50:50", List.of("ratio", "male", "female"), "ratio"));
				chatBotService.saveQnA(new QnA(null, "yikes", List.of("ranking", "rank"), "campus"));
				chatBotService.saveQnA(new QnA(null, "Tuition fee is 3500 Euro", List.of("fee", "price"), "campus"));
				chatBotService.saveQnA(new QnA(null, "sorry classes are full", List.of("application", "enroll"), "campus"));

				studentService.saveFaculty(new Faculty(null, "it", List.of("ujjwal", "testing"), "warsaw"));
				studentService.saveFaculty(new Faculty(null, "arts", List.of("sattyda"), "banglore"));
				studentService.saveFaculty(new Faculty(null, "music", List.of("test"), "mumbai"));
				studentService.saveFaculty(new Faculty(null, "kungfu", List.of("ujjwalone"), "paris"));

				studentService.saveFees(new Fees(null, 3500L, true, "ujjwal"));
				studentService.saveFees(new Fees(null, 10000L, false, "sattyda"));
				studentService.saveFees(new Fees(null, 100L, true, "tester"));
				studentService.saveFees(new Fees(null, 4000L, false, "ujjwaltwo"));

				studentService.saveSubject(new Subject(null, "spring boot", 8D, true, List.of("ujjwal", "testing")));
				studentService.saveSubject(new Subject(null, "java", 2D, false, List.of("ujjwal", "sattyda", "xyz")));
				studentService.saveSubject(new Subject(null, "maths", 9.5D, true, List.of("sattyda")));
				studentService.saveSubject(new Subject(null, "python", 0D, false, List.of("ujjwal", "testing")));
				studentService.saveSubject(new Subject(null, "cryptology", 4.2D, true, List.of("test", "testing")));
				studentService.saveSubject(new Subject(null, "frontend", 1D, false, List.of("ujjwalone", "testing")));
				studentService.saveSubject(new Subject(null, "database", 7D, true, List.of("john")));
//
				studentService.saveStudent(new Student(null, 52911L, 1005L, "ujjwal", "ujjwal", null, null, null));
				studentService.saveStudent(new Student(null, 52678L, 1L, "sattyda", "sattyda", null, null, null));
				studentService.saveStudent(new Student(null, 24975L, 1053L, "test", "test", null, null, null));
				studentService.saveStudent(new Student(null, 12345L, 2524L, "tester", "tester", null, null, null));
				studentService.saveStudent(new Student(null, 545L, 246L, "ghost", "ghost", null, null, null));

				studentService.addFacultyToStudent("ujjwal", "it");
				studentService.addFacultyToStudent("sattyda", "arts");
				studentService.addFacultyToStudent("test", "music");
//				studentService.addFacultyToStudent("ujjwalone", "kungfu");

				studentService.addFeesToStudent("ujjwal");
				studentService.addFeesToStudent("sattyda");
				studentService.addFeesToStudent("tester");
//				studentService.addFeesToStudent("ujjwaltwo");

				studentService.addSubjectToStudent("ujjwal");
				studentService.addSubjectToStudent("sattyda");
				studentService.addSubjectToStudent("test");
				studentService.addSubjectToStudent("tester");
//				studentService.addSubjectToStudent("ujjwalone");

			} catch (Exception e) {
				throw new NullPointerException("null found exception");
			}

		};
	}

}
