package chatbot.chatbot.controllers;

import chatbot.chatbot.entities.*;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import chatbot.chatbot.utilities.ChatBody;
import chatbot.chatbot.utilities.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

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
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		studentService.saveStudent(student);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/save").toUriString());
		return ResponseEntity.created(uri).body(student);
	}

//	public String chatBot(String message) {
//		Authentication authetication = SecurityContextHolder.getContext().getAuthentication();
//		if (authetication != null && authetication.getPrincipal() instanceof UserDetails userDetails) {
//			String[] studentProperties = {"name", "email", "department", "faculty", "mobile"};
//			if (Arrays.stream(studentProperties).toList().contains(message.toLowerCase())) {
//				User user = userService.findByUsername(userDetails.getUsername());
//				switch (message.toLowerCase()) {
//					case "name":
//						return user.getName();
//					case "email":
//						return user.getEmail();
////					case "department":
////						return user.getDepartment();
////					case "faculty":
////						return user.getFaculty();
////					case "mobile":
////						return user.getMobile();
//				}
//			}
//		}
//		return chatBotService.isInQnA(message);
//	}

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

		String botResponse = chatBotService.isInQnA((chatBody.getMessage()));

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


}
