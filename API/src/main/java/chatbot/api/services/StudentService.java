package chatbot.api.services;

import chatbot.chatbot.entities.Faculty;
import chatbot.chatbot.entities.Fees;
import chatbot.chatbot.entities.Student;
import chatbot.chatbot.entities.Subject;
import chatbot.chatbot.repositories.FacultyRepo;
import chatbot.chatbot.repositories.FeesRepo;
import chatbot.chatbot.repositories.StudentRepo;
import chatbot.chatbot.repositories.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
	@Autowired
	StudentRepo studentRepo;
	@Autowired
	FacultyRepo facultyRepo;
	@Autowired
	FeesRepo feesRepo;
	@Autowired
	SubjectRepo subjectRepo;


	public void saveStudent(Student student) {
//		student.setUsername(principal.getName());
		studentRepo.save(student);
	}

	public void saveFaculty(Faculty faculty) {
		facultyRepo.save(faculty);
	}

	public void saveSubject(Subject subject) {
		subjectRepo.save(subject);
	}

	public void saveFees(Fees fees) {
		feesRepo.save(fees);
	}

	public void addFacultyToStudent(String username, String facultyName) {
		Student student = studentRepo.findByUsername(username);
		if (student == null){
			return;
		}
		Faculty faculty = facultyRepo.findByName(facultyName);
		if (faculty == null){
			return;
		}
		student.setFaculty(faculty);
		studentRepo.save(student);

	}

	public void addFeesToStudent(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student == null){
			return;
		}
		Fees fees = feesRepo.findByUsername(username);
		if (fees == null){
			return;
		}
		student.setFees(fees);
		studentRepo.save(student);

	}

	public void addSubjectToStudent(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student == null){
			return;
		}
		List<Subject> subjectList = subjectRepo.findAllByUsername(username);
		if (subjectList.isEmpty()){
			return;
		}
		student.setSubject(subjectList);
		studentRepo.save(student);

	}

	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}
}