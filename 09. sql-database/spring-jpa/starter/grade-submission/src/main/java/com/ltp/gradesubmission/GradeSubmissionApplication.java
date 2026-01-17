package com.ltp.gradesubmission;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.StudentRepository;

@SpringBootApplication
public class GradeSubmissionApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Student[] students = new Student[] { 
            new Student(null, "Harry Potter", LocalDate.parse(("1980-07-31"))),
            new Student(null, "Ron Weasley", LocalDate.parse(("1980-03-01"))),
            new Student(null, "Hermione Granger", LocalDate.parse(("1979-09-19"))),
            new Student(null, "Neville Longbottom", LocalDate.parse(("1980-07-30"))) 
        };

		for (Student student : students) {
			studentRepository.save(student);
		}
	}
}
