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

//	@Bean
//	BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	CommandLineRunner run(UserService userService, StudentService studentService, ChatBotService chatBotService) {
		return args -> {
			try {
				userService.saveUser(new User(null, "ujjwal", "ujjwal@g.in", "ujjwal", "ujjwal", "ROLE_ADMIN", true));
				userService.saveUser(new User(null, "sattyda", "sattyda@g.in", "sattyda", "sattyda", "ROLE_USER", true));
				userService.saveUser(new User(null, "test", "test@g.in", "test", "test", "ROLE_STUDENT", true));
				userService.saveUser(new User(null, "tester", "tester@g.in", "tester", "tester", "ROLE_TEACHER", true));

				chatBotService.saveQnA(new QnA( null, "Campus Size" , "1200acres" , "Campus" ));
				chatBotService.saveQnA(new QnA( null, "Campus Color" , "Green" , "Campus" ));
				chatBotService.saveQnA(new QnA( null, "Number of Departments" , "10" , "Department" ));
				chatBotService.saveQnA(new QnA( null, "Male Female Ratio" , "50:50" , "Ratio" ));

//				userService.saveRole(new Role(null, "ROLE_USER"));
//				userService.saveRole(new Role(null, "ROLE_ADMIN"));
//				userService.saveRole(new Role(null, "ROLE_TEACHER"));
//				userService.saveRole(new Role(null, "ROLE_STUDENT"));
//				userService.saveRole(new Role(null, "ROLE_GOD"));
//
//				userService.addRoleToUser("ujjwal", "ROLE_ADMIN");
//				userService.addRoleToUser("sattyda", "ROLE_USER");
//				userService.addRoleToUser("test", "ROLE_STUDENT");
//				userService.addRoleToUser("ujjwal", "ROLE_TEACHER");

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

				studentService.saveStudent(new Student(null, 52911L, 1005L, "ujjwal", "ujjwal", null, null, null));
				studentService.saveStudent(new Student(null, 52678L, 1L, "sattyda", "sattyda", null, null, null));
				studentService.saveStudent(new Student(null, 24975L, 1053L, "test", "test", null, null, null));
				studentService.saveStudent(new Student(null, 12345L, 2524L, "tester", "tester", null, null, null));
				studentService.saveStudent(new Student(null, 545L, 246L, "ghost", "ghost", null, null, null));

				studentService.addFacultyToStudent("ujjwal", "it");
				studentService.addFacultyToStudent("sattyda", "arts");
				studentService.addFacultyToStudent("test", "music");
				studentService.addFacultyToStudent("ujjwalone", "kungfu");

				studentService.addFeesToStudent("ujjwal");
				studentService.addFeesToStudent("sattyda");
				studentService.addFeesToStudent("tester");
				studentService.addFeesToStudent("ujjwaltwo");

				studentService.addSubjectToStudent("ujjwal");
				studentService.addSubjectToStudent("sattyda");
				studentService.addSubjectToStudent("test");
				studentService.addSubjectToStudent("tester");
				studentService.addSubjectToStudent("ujjwalone");

			} catch (Exception e) {
				throw new NullPointerException("null found exception");
			}

		};
	}

}
