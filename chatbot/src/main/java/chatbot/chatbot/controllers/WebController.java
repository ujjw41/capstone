package chatbot.chatbot.controllers;


import chatbot.chatbot.entities.*;
import chatbot.chatbot.services.StudentService;
import chatbot.chatbot.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping("/0")
public class WebController {
	@Autowired
	UserService userService;
	@Autowired
	StudentService studentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerUser() {
		return "register_user";
	}

	@RequestMapping(value = "/student/register", method = RequestMethod.GET)
	public String registerStudent() {
		return "register_student";
	}

	@RequestMapping(value = "/admin" , method = RequestMethod.GET)
	public String admin(){
		return "admin";
	}


//	@ResponseBody
//	@WriteOperation
//	@PostMapping("/role/save")
//	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
//		userService.saveRole(role);
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
//		return ResponseEntity.created(uri).body(role);
//	}

//	@ResponseBody
//	@WriteOperation
//	@PostMapping("/user/addrole")
//	public ResponseEntity<?> addRoleToUser(@RequestBody User user, @RequestBody Role role) {
//		userService.addRoleToUser(user.getUsername(), role.getName());
//		return ResponseEntity.ok().build();
//	}

//	@GetMapping("/refresh/token")
//	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String refresh_token = request.getHeader(AUTHORIZATION);
//		if (refresh_token != null) {
//			try {
//				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//				JWTVerifier verifier = JWT.require(algorithm).build();
//				DecodedJWT decodedJWT = verifier.verify(refresh_token);
//				String username = decodedJWT.getSubject();
//				User user = userService.findByUsername(username);
//				String access_token = JWT.create()
//						.withSubject(user.getUsername())
//						.withExpiresAt(new Date(System.currentTimeMillis() +100 * 60 * 1000))
//						.withIssuer(request.getRequestURL().toString())
//						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
//						.sign(algorithm);
//				Map<String, String> tokens = new HashMap<>();
//				tokens.put("access_token", access_token);
//				tokens.put("refresh_token", refresh_token);
//				response.setContentType(APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//			} catch (Exception exception) {
//				response.setHeader("error", exception.getMessage());
//				response.setStatus(FORBIDDEN.value());
////					response.sendError(FORBIDDEN.value());
//				Map<String, String> error = new HashMap<>();
//				error.put("error_message", exception.getMessage());
//
//				response.setContentType(APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), error);
//			}
//		} else {
//			throw new  RuntimeException("refresh token is missing");
//		}
//
//	}

	@ResponseBody
	@WriteOperation
	@PostMapping("/student/save")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		studentService.saveStudent(student);
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



}
