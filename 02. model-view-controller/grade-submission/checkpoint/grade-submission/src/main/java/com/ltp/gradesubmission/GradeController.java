package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class GradeController {

    List<Grade> studentGrades = new ArrayList<>();

    @GetMapping("/") //All of this happens before form template is charged
    public String getForm(Model model, @RequestParam(required = false) String id) {
        int index = getGradeIndex(id);

        Grade grade = (index == Constants.NOT_FOUND) ? new Grade() : studentGrades.get(index);
        /*
         * If we had a react frontend we would do:
         * Serialize grade into json
         * Send it over to the web server where the frontend is running
         */
        //As we are using thymeleaf we have to do:
        model.addAttribute("grade", grade); //Bounds the grade to the thymeleaf template
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submit(Grade grade) {
        int index = getGradeIndex(grade.getId());
        if (index == Constants.NOT_FOUND) {
            //Add a new grade
            studentGrades.add(grade);
        } else {
            studentGrades.set(index, grade);
        }

        return "redirect:/grades";
    }
    

    @GetMapping("/grades")
    public String getGrades(Model model) {
        model.addAttribute("grades", studentGrades);
        return "grades";
    }

    public int getGradeIndex(String id) {
        for (int index = 0; index < studentGrades.size(); index++) {
            if (studentGrades.get(index).getId().equals(id)) {
                return index;
            }
        }
        return Constants.NOT_FOUND;
    }

}