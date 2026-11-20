
package PlacementPro.service;

import PlacementPro.entity.PlacementDrive;
import PlacementPro.repository.PlacementDriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementDriveService {

    private final PlacementDriveRepository placementDriveRepository;

    public PlacementDriveService(
            PlacementDriveRepository placementDriveRepository) {
        this.placementDriveRepository = placementDriveRepository;
    }

    // Add Placement Drive
    public PlacementDrive addDrive(PlacementDrive drive) {
        return placementDriveRepository.save(drive);
    }

    // Get All Placement Drives
    public List<PlacementDrive> getAllDrives() {
        return placementDriveRepository.findAll();
    }

    // Get Placement Drive By ID
    public PlacementDrive getDriveById(Long id) {
        return placementDriveRepository.findById(id).orElse(null);
    }

    // Delete Placement Drive
    public void deleteDrive(Long id) {
        placementDriveRepository.deleteById(id);
    }
    // Get total placement drives
    public long getTotalDrives() {
        return placementDriveRepository.count();
    }
}
