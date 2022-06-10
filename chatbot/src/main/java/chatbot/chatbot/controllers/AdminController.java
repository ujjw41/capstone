package chatbot.chatbot.controllers;

import chatbot.chatbot.entities.Chat;
import chatbot.chatbot.entities.Conversation;
import chatbot.chatbot.entities.QnA;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.utilities.ConversationInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	ChatBotService chatBotService;

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
}
