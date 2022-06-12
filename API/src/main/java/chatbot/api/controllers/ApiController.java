package chatbot.api.controllers;

import chatbot.api.enitites.*;
import chatbot.api.services.ChatBotService;
import chatbot.api.services.StudentService;
import chatbot.api.services.UserService;
import chatbot.api.utilities.ChatBody;
import chatbot.api.utilities.ConversationInput;
import chatbot.api.utilities.Response;
import chatbot.api.utilities.StudentTemplate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequestMapping("/app")
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

	@GetMapping("/students/all")
	public ResponseEntity<List<Student>> getStudents() {
		return ResponseEntity.ok().body(studentService.getAllStudents());
	}

	@GetMapping("/users/all")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}


	@ResponseBody
	@WriteOperation
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		userService.saveRole(role);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
		return ResponseEntity.created(uri).body(role);
	}

	@ResponseBody
	@WriteOperation
	@PostMapping("/user/addrole")
	public ResponseEntity<?> addRoleToUser(@RequestBody User user, @RequestBody Role role) {
		userService.addRoleToUser(user.getUsername(), role.getName());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/refresh/token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String refresh_token = request.getHeader(AUTHORIZATION);
		if (refresh_token != null) {
			try {
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				User user = userService.findByUsername(username);
				String access_token = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());

				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("refresh token is missing");
		}

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

	@ResponseBody
	@PostMapping(value = "/conversations")
	public ResponseEntity<List<Conversation>> conversations() {
		List<Conversation> response = chatBotService.getAllConversations();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/chats")
	public ResponseEntity<List<Chat>> messages(@RequestBody ConversationInput conversationInput) {
		List<Chat> response = chatBotService.getChatsByConversationId(conversationInput.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ResponseBody
	@WriteOperation
	@PostMapping("/qna/save")
	public ResponseEntity<QnA> saveStudent(@RequestBody QnA qnA) {
		chatBotService.saveQnA(qnA);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/qna/save").toUriString());
		return ResponseEntity.created(uri).body(qnA);
	}

	@ResponseBody
	@WriteOperation
	@PutMapping("/qna/update")
	public ResponseEntity<QnA> updateStudent(@RequestBody QnA qnA) {
		chatBotService.updateQnA(qnA);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/qna/update").toUriString());
		return ResponseEntity.created(uri).body(qnA);
	}

	@ResponseBody
	@DeleteOperation
	@DeleteMapping("/qna/delete")
	public void delete(@RequestBody QnA qnA) {
		chatBotService.deleteQnA(qnA);

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

}
