
package PlacementPro.controller;

import PlacementPro.entity.Application;
import PlacementPro.entity.PlacementDrive;
import PlacementPro.entity.Student;
import PlacementPro.service.ApplicationService;
import PlacementPro.service.PlacementDriveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final PlacementDriveService placementDriveService;

    public ApplicationController(
            ApplicationService applicationService,
            PlacementDriveService placementDriveService) {
        this.applicationService = applicationService;
        this.placementDriveService = placementDriveService;
    }

    @PostMapping("/apply/{driveId}")
    public String applyForDrive(
            @PathVariable Long driveId,
            HttpSession session) {

        Student student = (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        PlacementDrive drive =
                placementDriveService.getDriveById(driveId);

        if (drive == null) {
            return "redirect:/drives?notfound";
        }

        Application application = new Application();
        application.setStudent(student);
        application.setPlacementDrive(drive);

        try {
            applicationService.apply(application);
            return "redirect:/drives?applied";

        } catch (IllegalArgumentException e) {

            String error = e.getMessage();

            if (error.contains("CGPA")) {
                return "redirect:/drives?ineligibleCgpa";
            }

            if (error.contains("branch")) {
                return "redirect:/drives?ineligibleBranch";
            }

            if (error.contains("deadline")) {
                return "redirect:/drives?deadlinePassed";
            }

            if (error.contains("already applied")) {
                return "redirect:/drives?alreadyApplied";
            }

            return "redirect:/drives?error";
        }
    }
}
