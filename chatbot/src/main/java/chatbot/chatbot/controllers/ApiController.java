package chatbot.chatbot.controllers;

import chatbot.chatbot.entities.*;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import chatbot.chatbot.utilities.ChatBody;
import chatbot.chatbot.utilities.Response;
import chatbot.chatbot.utilities.StudentTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {
	@Autowired
	UserService userService;
	@Autowired
	StudentService studentService;
	@Autowired
	ChatBotService chatBotService;

	@ResponseBody
	@WriteOperation
	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user, BindingResult result) {
		userService.saveUser(user);
		user.setPassword(null);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
		return ResponseEntity.created(uri).body(user);
	}

	@ResponseBody
	@WriteOperation
	@PostMapping("/student/save")
	public ResponseEntity<Student> saveStudent(@RequestBody StudentTemplate studentTemplate) {
		//TODO: getting name, username from the userdetails instead of the user input

		Student student = new Student();
		student.setStudentId(studentTemplate.getStudentId());
		student.setLibraryCardNumber(studentTemplate.getLibraryCardNumber());
		student.setName(studentTemplate.getName());
		student.setUsername(studentTemplate.getUsername());
		studentService.saveStudent(student);

		Fees fees = new Fees();
		fees.setUsername(student.getUsername());
		studentService.saveFees(fees);
		studentService.addFeesToStudent(student.getUsername());

		Faculty faculty = studentService.findFacultyByName(studentTemplate.getFaculty());
		if (faculty == null) {
			Faculty faculty1 = new Faculty();
			faculty1.setName(studentTemplate.getFaculty());
			faculty1.setUsername(List.of(studentTemplate.getFaculty()));
			studentService.saveFaculty(faculty1);
			studentService.addFacultyToStudent(student.getUsername(), faculty1.getName());
		} else {
			studentService.saveFaculty(faculty);
			studentService.addFacultyToStudent(student.getUsername(), faculty.getName());
		}

// 		TODO: implement getting array of string of subject names from the user and adding it to the student

//		log.info(Arrays.toString(studentTemplate.getSubjects()));
//		List<Subject> subjects = new ArrayList<>();
//		List<String> subjectsNotFound = new ArrayList<>();
//		List<String> subjectNameList = new ArrayList<>(Arrays.stream(studentTemplate.getSubject()).toList());
//
//		subjectNameList.forEach(subjectName -> {
//			Subject subject = studentService.findSubjectByName(subjectName);
//			if (subject == null) {
//				subjectsNotFound.add(subjectName);
//			} else {
//				subjects.add(subject);
//			}
//		});
//
//		subjectsNotFound.forEach(subjectName ->{
//			Subject subject = new Subject();
//			subject.setName(subjectName);
//			studentService.saveSubject(subject);
//			subjects.add(subject);
//		});
//
//		subjects.forEach(subject -> {
//			subject.getUsername().add(student.getUsername());
//			studentService.saveSubject(subject);
//		});

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/save").toUriString());
		return ResponseEntity.created(uri).body(student);
	}

	public String chatBot(String message) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
			String[] studentProperties = {"name", "email", "department", "faculty", "mobile"};
			if (Arrays.stream(studentProperties).toList().contains(message.toLowerCase())) {
				User user = userService.findByUsername(userDetails.getUsername());
				Student student = studentService.findStudentByUsername(userDetails.getUsername());
				if (!(student == null)) {
					switch (message.toLowerCase()) {
						case "name" -> {
							log.info(user.getName());
							return user.getName();
						}
						case "email" -> {
							log.info(user.getEmail());
							return user.getEmail();
						}
						case "studentid" -> {
							log.info(String.valueOf(student.getStudentId()));
							return String.valueOf(student.getStudentId());
						}
						case "library" -> {
							log.info(String.valueOf(student.getLibraryCardNumber()));
							return String.valueOf(student.getLibraryCardNumber());
						}
						case "faculty" -> {
							log.info(String.valueOf(student.getFaculty()));
							return String.valueOf(student.getFaculty());
						}
						case "fees" -> {
							log.info(String.valueOf(student.getFees()));
							return String.valueOf(student.getFees());
						}
						case "subject" -> {
							log.info(String.valueOf(student.getSubject()));
							return String.valueOf(student.getSubject());
						}
					}
				}
			}
		}
		return chatBotService.isInQnA(message);
	}

	@ResponseBody
	@PostMapping(value = "/chatbot")
	public ResponseEntity<Response> chatBot(@RequestBody ChatBody chatBody) {
		String userName = "anonymous";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
			userName = userDetails.getUsername();
		}
		if (chatBody.getIndex() == 0) {
			Conversation con = chatBotService.createConversation(userName);
			chatBody.setIndex(con.getId());
		}

		chatBotService.saveChat(chatBody.getIndex(), userName, chatBody.getMessage(), "user");

		String botResponse = chatBot(chatBody.getMessage());

		Response response = new Response();
		response.setStatus("success");
		response.setData(botResponse);
		response.setConvId(chatBody.getIndex());

		chatBotService.saveChat(chatBody.getIndex(), userName, botResponse, "bot");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/student/addfaculty")
	public ResponseEntity<?> addFacultyToStudent(@RequestBody Student student, @RequestBody Faculty faculty) throws Exception {
		studentService.addFacultyToStudent(student.getUsername(), faculty.getName());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/student/addfees")
	public ResponseEntity<?> addFeesToStudent(@RequestBody Student student) {
		studentService.addFeesToStudent(student.getUsername());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/student/addsubjects")
	public ResponseEntity<?> addSubjectsToStudent(@RequestBody Student student) {
		studentService.addSubjectToStudent(student.getUsername());
		return ResponseEntity.ok().build();
	}

	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class
				.isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	@GetMapping("/admin/student/all")
	public ResponseEntity<List<Student>> getStudents() {
		return ResponseEntity.ok().body(studentService.getAllStudents());
	}

	@GetMapping("/admin/user/all")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}


}
