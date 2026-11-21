
package PlacementPro.controller;

import PlacementPro.entity.PlacementDrive;
import PlacementPro.service.PlacementDriveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PlacementDriveController {

    private final PlacementDriveService placementDriveService;

    public PlacementDriveController(PlacementDriveService placementDriveService) {
        this.placementDriveService = placementDriveService;
    }

    @GetMapping("/drives")
    public String showPlacementDrives(Model model, HttpSession session) {

        List<PlacementDrive> drives = placementDriveService.getAllDrives();
        model.addAttribute("drives", drives);

        boolean isAdmin = Boolean.TRUE.equals(
                session.getAttribute("loggedInAdmin"));

        model.addAttribute("isAdmin", isAdmin);

        return "drives";
    }

    @GetMapping("/admin/drives/edit/{id}")
    public String showEditDriveForm(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (!Boolean.TRUE.equals(session.getAttribute("loggedInAdmin"))) {
            return "redirect:/admin/login";
        }

        PlacementDrive drive = placementDriveService.getDriveById(id);

        if (drive == null) {
            return "redirect:/drives";
        }

        model.addAttribute("drive", drive);
        return "edit-drive";
    }

    @PostMapping("/admin/drives/update/{id}")
    public String updateDrive(
            @PathVariable Long id,
            @ModelAttribute PlacementDrive updatedDrive,
            HttpSession session) {

        if (!Boolean.TRUE.equals(session.getAttribute("loggedInAdmin"))) {
            return "redirect:/admin/login";
        }

        PlacementDrive existingDrive = placementDriveService.getDriveById(id);

        if (existingDrive == null) {
            return "redirect:/drives";
        }

        updatedDrive.setId(id);
        placementDriveService.addDrive(updatedDrive);

        return "redirect:/drives";
    }
}
