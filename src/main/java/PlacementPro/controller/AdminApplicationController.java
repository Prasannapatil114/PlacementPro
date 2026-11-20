
package PlacementPro.controller;

import PlacementPro.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminApplicationController {

    private final ApplicationService applicationService;

    public AdminApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // View all student applications
    @GetMapping("/admin/applications")
    public String showAllApplications(Model model) {

        model.addAttribute(
                "applications",
                applicationService.getAllApplications()
        );

        return "admin-applications";
    }

    // Update application status
    @PostMapping("/admin/update-application")
    public String updateApplicationStatus(
            @RequestParam Long applicationId,
            @RequestParam String status) {

        if (!status.equals("Pending")
                && !status.equals("Selected")
                && !status.equals("Rejected")) {
            return "redirect:/admin/applications?error";
        }

        applicationService.updateApplicationStatus(applicationId, status);

        return "redirect:/admin/applications?updated";
    }
}
