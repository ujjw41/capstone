package chatbot.api.enitites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Fees {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long totalAmount= 3500L;
	boolean status= false;
	String username;


}
