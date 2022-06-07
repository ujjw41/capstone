package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(columnDefinition="TEXT")
	String question;

	@Column(columnDefinition="TEXT")
	String answer;

	String keywords;
}
