
package PlacementPro.controller;

import PlacementPro.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentManagementController {

    private final StudentService studentService;

    public StudentManagementController(StudentService studentService) {
        this.studentService = studentService;
    }

    // View and search students
    @GetMapping("/admin/students")
    public String showAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String branch,
            Model model) {

        if (name != null && !name.trim().isEmpty()) {
            model.addAttribute(
                    "students",
                    studentService.searchStudents(name.trim())
            );
        } else if (branch != null && !branch.trim().isEmpty()) {
            model.addAttribute(
                    "students",
                    studentService.filterStudentsByBranch(branch.trim())
            );
        } else {
            model.addAttribute(
                    "students",
                    studentService.getAllStudents()
            );
        }

        model.addAttribute("searchName", name);
        model.addAttribute("selectedBranch", branch);

        return "admin-students";
    }
}
