package chatbot.chatbot.controllers;


import chatbot.chatbot.entities.QnA;
import chatbot.chatbot.services.ChatBotService;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {
	@Autowired
	UserService userService;
	@Autowired
	StudentService studentService;
	@Autowired
	ChatBotService chatBotService;

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	@GetMapping(value = "/register")
	public String registerUser() {
		return "register_user";
	}

	@GetMapping(value = "/student/register")
	public String registerStudent() {
		return "register_student";
	}

	@GetMapping(value = "/conversations")
	public String conversations() {
		return "conversations";
	}

	@GetMapping(value = "/admin/qna/view")
	public String qnAView(Model model) {
		List<QnA> allQnA = chatBotService.getAllQnA();
		model.addAttribute("qnas", allQnA);
		return "qna_view";
	}

	@GetMapping(value = "/admin/qna/save")
	public String qnASave() {
		return "qna_save";
	}

	@GetMapping(value = "/admin/qna/delete")
	public String qnADelete() {
		return "qna_delete";
	}

	@GetMapping(value = "/admin/qna/update")
	public String qnAUpdate() {
		return "qna_update";
	}


}
