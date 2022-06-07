package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String username;
	String message;
	String owner;
	Date time;
}
