package chatbot.chatbot.controllers;


import chatbot.chatbot.entities.User;
import chatbot.chatbot.services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
	@Autowired
	WebService webService;

	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public String index(){
		return "index";
	}

	@RequestMapping(value = "/register" , method = RequestMethod.GET)
	public String register(){
		return "register";
	}

	@ResponseBody
	@WriteOperation
	@PostMapping("/save")
	public ResponseEntity<User> register(@RequestBody User user, BindingResult result) {
		webService.saveUser(user);

		user.setPassword(null);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
