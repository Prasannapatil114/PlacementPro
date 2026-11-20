
package PlacementPro.controller;

import PlacementPro.entity.PlacementDrive;
import PlacementPro.service.PlacementDriveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PlacementDriveController {

    private final PlacementDriveService placementDriveService;

    public PlacementDriveController(
            PlacementDriveService placementDriveService) {
        this.placementDriveService = placementDriveService;
    }

    // Display All Placement Drives
    @GetMapping("/drives")
    public String showPlacementDrives(
            Model model, HttpSession session) {

        List<PlacementDrive> drives =
                placementDriveService.getAllDrives();

        model.addAttribute("drives", drives);

        // Identify whether the visitor is an admin
        boolean isAdmin = Boolean.TRUE.equals(
                session.getAttribute("loggedInAdmin"));

        model.addAttribute("isAdmin", isAdmin);

        return "drives";
    }
}
