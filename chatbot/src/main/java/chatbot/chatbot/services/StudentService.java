package chatbot.chatbot.services;

import chatbot.chatbot.entities.Faculty;
import chatbot.chatbot.entities.Fees;
import chatbot.chatbot.entities.Student;
import chatbot.chatbot.entities.Subject;
import chatbot.chatbot.repositories.FacultyRepo;
import chatbot.chatbot.repositories.FeesRepo;
import chatbot.chatbot.repositories.StudentRepo;
import chatbot.chatbot.repositories.SubjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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

	public Student findStudentByUsername(String username) {
		return studentRepo.findByUsername(username);
	}

	public Faculty findFacultyByName(String facultyName) {
		return facultyRepo.findByName(facultyName);
	}

	public Subject findSubjectByName(String subjectName) {
		return subjectRepo.findByName(subjectName);
	}

	public void addFacultyToStudent(String username, String facultyName) {
		Student student = studentRepo.findByUsername(username);
		if (student == null) {
			log.error("username not found : {}", username);
			return;
		}
		Faculty faculty = facultyRepo.findByName(facultyName);
		if (faculty == null) {
			log.error("faculty not found: {}", facultyName);
			return;
		}
//		else {
//			faculty.getUsername().add(student.getUsername());
//		}
		student.setFaculty(faculty);
		studentRepo.save(student);

	}

	public void addFeesToStudent(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student == null) {
			log.error("username not found: {}", username);
			return;
		}
		Fees fees = feesRepo.findByUsername(username);
		if (fees == null) {
			log.error("fees not found for: {}", username);
			return;
		}
		student.setFees(fees);
		studentRepo.save(student);

	}

	public void addSubjectToStudent(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student == null) {
			log.error("username not found: {}", username);
			return;
		}
		List<Subject> subjectList = subjectRepo.findAllByUsername(username);
		if (subjectList.isEmpty()) {
			log.error("no subjects were found for the username: {}", username);
			return;
		}
		student.setSubject(subjectList);
		studentRepo.save(student);

	}

	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}
}
