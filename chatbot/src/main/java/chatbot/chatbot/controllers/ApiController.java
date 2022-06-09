package chatbot.chatbot.controllers;

import chatbot.chatbot.entities.*;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import chatbot.chatbot.utilities.ChatInput;
import chatbot.chatbot.utilities.MessageBody;
import chatbot.chatbot.utilities.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.Principal;
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

	public String chatbot(String message) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			log.info("user is logged in");
			UserDetails userDetails = (UserDetails) principal;
		}
		String qna = chatBotService.isInQnA(message);
		return qna;

	}

	@ResponseBody
	@RequestMapping(value = "/api/hello", method = RequestMethod.POST)
	public ResponseEntity<Response> hello(@RequestBody MessageBody messageBody, Principal principal) {
		String user = "anonymous";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
			user = userDetails.getUsername();
		}
		if (messageBody.getIndex() == 0) {
			Conversation con = chatBotService.createConversation(user);
			messageBody.setIndex(con.getId());
		}

		chatBotService.saveChat(messageBody.getIndex(), user, messageBody.getMessage(), "user");

		String botResponse = satbot(messageBody.getMessage());

		Response response = new Response();
		response.setStatus("success");
		response.setData(botResponse);
		response.setConvId(messageBody.getIndex());

		chatBotService.saveChat(messageBody.getIndex(), user, botResponse, "bot");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public String satbot(String message) {

		Authentication authetication = SecurityContextHolder.getContext().getAuthentication();

		if (authetication != null && authetication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authetication.getPrincipal();
			String[] userProperties = {"name", "email", "department", "faculty", "mobile"};
			if (Arrays.stream(userProperties).toList().contains(message.toLowerCase())) {
				User user = userService.findByUsername(userDetails.getUsername());
				switch (message.toLowerCase()) {
					case "name":
						return user.getName();
					case "email":
						return user.getEmail();
//					case "department":
//						return user.getDepartment();
//					case "faculty":
//						return user.getFaculty();
//					case "mobile":
//						return user.getMobile();
				}
			}
		}
		String qna = chatBotService.isInQnA(message);
		return qna;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/conversations", method = RequestMethod.POST)
	public ResponseEntity<List<Conversation>> conversations() {
		List<Conversation> response = chatBotService.getConversations();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/messages", method = RequestMethod.POST)
	public ResponseEntity<List<Chat>> messages(@RequestBody ChatInput chatInput) {
		List<Chat> response = chatBotService.getChats(chatInput.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/student/addfaculty")
	public ResponseEntity<?> addFacultyToStudent(@RequestBody Student student, @RequestBody Faculty faculty) {
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
