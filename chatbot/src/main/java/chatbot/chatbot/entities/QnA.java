package chatbot.chatbot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(columnDefinition="TEXT")
	String answer;

	@ElementCollection
	List<String> keywords;

	String category;
}
