
package PlacementPro.controller;

import PlacementPro.entity.PlacementDrive;
import PlacementPro.service.PlacementDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class AdminController {

    private final PlacementDriveService placementDriveService;

    public AdminController(
            PlacementDriveService placementDriveService) {
        this.placementDriveService = placementDriveService;
    }

    // Show Add Placement Drive Page
    @GetMapping("/admin/add-drive")
    public String showAddDriveForm(Model model) {

        model.addAttribute("drive", new PlacementDrive());

        return "add-drive";
    }

    // Save Placement Drive
    @PostMapping("/admin/add-drive")
    public String addPlacementDrive(
            @ModelAttribute PlacementDrive drive) {

        placementDriveService.addDrive(drive);

        return "redirect:/drives";
    }
}
