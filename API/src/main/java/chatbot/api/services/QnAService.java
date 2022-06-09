package chatbot.api.services;

import chatbot.chatbot.entities.QnA;
import chatbot.chatbot.repositories.QnARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnAService {
	@Autowired
	QnARepo qnARepo;

	public void saveQnA(QnA qnA) {
		qnARepo.save(qnA);
	}

	public List<QnA> getAllQnA(){
		return qnARepo.findAll();
	}
}
