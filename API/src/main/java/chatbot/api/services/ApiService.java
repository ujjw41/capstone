package chatbot.api.services;

import chatbot.api.enitites.Faculty;
import chatbot.api.enitites.Fees;
import chatbot.api.enitites.Subjects;
import chatbot.api.repositories.FacultyRepo;
import chatbot.api.repositories.FeesRepo;
import chatbot.api.repositories.SubjectsRepo;
import chatbot.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
	@Autowired
	FacultyRepo facultyRepo;
	@Autowired
	FeesRepo feesRepo;
	@Autowired
	SubjectsRepo subjectsRepo;

//	@Autowired
//	UserRepo userRepo;

	public void saveFaculty(Faculty faculty) {
		facultyRepo.save(faculty);
	}

	public void saveFees(Fees fees) {
		feesRepo.save(fees);
	}

	public void saveSubjects(Subjects subjects) {
		subjectsRepo.save(subjects);
	}


}
