
package PlacementPro.controller;

import PlacementPro.entity.Student;
import PlacementPro.service.ApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyApplicationsController {

    private final ApplicationService applicationService;

    public MyApplicationsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Display applications of logged-in student
    @GetMapping("/my-applications")
    public String showMyApplications(
            HttpSession session, Model model) {

        Student student =
                (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "applications",
                applicationService.getStudentApplications(student.getId())
        );

        return "my-applications";
    }
}
