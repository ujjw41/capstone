package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long conversation;
	String username;
	@Column(columnDefinition = "TEXT")
	String message;
	String owner;
	Date time;
}
