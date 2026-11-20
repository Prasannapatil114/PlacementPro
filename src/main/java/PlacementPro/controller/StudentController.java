
package PlacementPro.controller;

import PlacementPro.entity.Student;
import PlacementPro.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Registration Page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Register Student
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {

        try {
            studentService.registerStudent(student);
            return "redirect:/register?success";

        } catch (IllegalArgumentException e) {
            return "redirect:/register?error";
        }
    }

    // Login Page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Student Login
    @PostMapping("/login")
    public String loginStudent(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        Student student = studentService.loginStudent(email, password);

        if (student != null) {
            session.setAttribute("loggedInStudent", student);
            return "redirect:/dashboard";
        }

        return "redirect:/login?error";
    }

    // Student Dashboard
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {

        Student student =
                (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        return "dashboard";
    }

    // Student Profile
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {

        Student student =
                (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        return "profile";
    }

    // Show Edit Profile Page
    @GetMapping("/edit-profile")
    public String showEditProfile(HttpSession session, Model model) {

        Student student =
                (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        return "edit-profile";
    }

    // Save Updated Profile
    @PostMapping("/edit-profile")
    public String updateProfile(
            @ModelAttribute Student updatedStudent,
            HttpSession session) {

        Student existingStudent =
                (Student) session.getAttribute("loggedInStudent");

        if (existingStudent == null) {
            return "redirect:/login";
        }

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setBranch(updatedStudent.getBranch());
        existingStudent.setCgpa(updatedStudent.getCgpa());
        existingStudent.setGraduationYear(
                updatedStudent.getGraduationYear());

        Student savedStudent =
                studentService.updateStudent(existingStudent);

        session.setAttribute("loggedInStudent", savedStudent);

        return "redirect:/profile?updated";
    }

    // Student Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/login?logout";
    }
}

