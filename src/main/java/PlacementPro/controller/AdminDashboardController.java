
package PlacementPro.controller;

import PlacementPro.service.ApplicationService;
import PlacementPro.service.PlacementDriveService;
import PlacementPro.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    private final StudentService studentService;
    private final PlacementDriveService placementDriveService;
    private final ApplicationService applicationService;

    public AdminDashboardController(
            StudentService studentService,
            PlacementDriveService placementDriveService,
            ApplicationService applicationService) {

        this.studentService = studentService;
        this.placementDriveService = placementDriveService;
        this.applicationService = applicationService;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {

        model.addAttribute("totalStudents",
                studentService.getTotalStudents());

        model.addAttribute("branchStats",
                studentService.getStudentsByBranch());

        model.addAttribute("totalDrives",
                placementDriveService.getTotalDrives());

        model.addAttribute("totalApplications",
                applicationService.getTotalApplications());

        model.addAttribute("selectedApplications",
                applicationService.getSelectedApplications());

        model.addAttribute("rejectedApplications",
                applicationService.getRejectedApplications());

        model.addAttribute("pendingApplications",
                applicationService.getPendingApplications());

        return "admin-dashboard";
    }
}
