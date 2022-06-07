package chatbot.api.enitites;

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
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String user;
	Date startTime;
}
