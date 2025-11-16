package com.ltp.gradesubmission.Service;

import java.util.ArrayList;
import java.util.List;

import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.Repository.GradeRepository;

public class GradeService {

    GradeRepository gradeRepository = new GradeRepository();

    public Grade getGrade(int index) {
        return gradeRepository.getGrade(index);
    }

    public void addGrade(Grade grade) {
        gradeRepository.addGrade(grade);
    }

    public void updateGrade(int index, Grade grade) {
        gradeRepository.updateGrade(index, grade);  
    }

    public List<Grade> getGrades() {
        return gradeRepository.getGrades();
    }
}
