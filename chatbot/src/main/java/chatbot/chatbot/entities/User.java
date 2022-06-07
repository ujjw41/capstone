package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;

	@Email
	@Column(unique = true)
	String email;

	//	@NotBlank(message = "username is required")
	@Column(unique = true, nullable = false)
	String username;

	//	@NotBlank(message = "password is required")
	@Column(nullable = false)
	String password;

//	@Column(columnDefinition = "varchar(255) default 'USER'")
	String role;

	@Column(columnDefinition = "boolean default true")
	Boolean enabled = true;
}
