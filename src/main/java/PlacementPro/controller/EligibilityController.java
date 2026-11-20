
package PlacementPro.controller;

import PlacementPro.entity.Student;
import PlacementPro.entity.PlacementDrive;
import PlacementPro.service.PlacementDriveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EligibilityController {

    private final PlacementDriveService placementDriveService;

    public EligibilityController(
            PlacementDriveService placementDriveService) {
        this.placementDriveService = placementDriveService;
    }

    @GetMapping("/eligibility")
    public String checkEligibility(
            HttpSession session, Model model) {

        Student student = (Student) session.getAttribute("loggedInStudent");

        if (student == null) {
            return "redirect:/login";
        }

        List<PlacementDrive> allDrives =
                placementDriveService.getAllDrives();

        List<PlacementDrive> eligibleDrives = allDrives.stream()
                .filter(drive -> {

                    boolean cgpaEligible =
                            student.getCgpa() != null
                                    && drive.getMinimumCgpa() != null
                                    && student.getCgpa() >= drive.getMinimumCgpa();

                    boolean branchEligible =
                            drive.getEligibleBranch() != null
                                    && student.getBranch() != null
                                    && List.of(drive.getEligibleBranch().split(","))
                                    .stream()
                                    .anyMatch(branch ->
                                            branch.trim().equalsIgnoreCase(
                                                    student.getBranch()
                                            )
                                    );

                    return cgpaEligible && branchEligible;
                })
                .toList();

        model.addAttribute("student", student);
        model.addAttribute("eligibleDrives", eligibleDrives);
        model.addAttribute("totalDrives", allDrives.size());
        model.addAttribute("eligibleCount", eligibleDrives.size());

        return "eligibility";
    }
}
