
package PlacementPro.service;

import PlacementPro.entity.Application;
import PlacementPro.entity.Student;
import PlacementPro.entity.PlacementDrive;
import PlacementPro.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // Apply for a placement drive
    public Application apply(Application application) {

        Student student = application.getStudent();
        PlacementDrive drive = application.getPlacementDrive();

        Long studentId = student.getId();
        Long driveId = drive.getId();

        // Check minimum CGPA
        if (student.getCgpa() == null ||
                drive.getMinimumCgpa() == null ||
                student.getCgpa() < drive.getMinimumCgpa()) {

            throw new IllegalArgumentException(
                    "You do not meet the minimum CGPA requirement!");
        }

        // Check eligible branch
        boolean branchEligible = Arrays.stream(
                        drive.getEligibleBranch().split(","))
                .map(String::trim)
                .anyMatch(branch ->
                        branch.equalsIgnoreCase(student.getBranch()));

        if (!branchEligible) {
            throw new IllegalArgumentException(
                    "Your branch is not eligible for this drive!");
        }

        // Check application deadline
        if (drive.getApplicationDeadline() != null &&
                drive.getApplicationDeadline().isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "The application deadline has passed!");
        }

        // Prevent duplicate applications
        boolean alreadyApplied =
                applicationRepository.existsByStudentIdAndPlacementDriveId(
                        studentId, driveId);

        if (alreadyApplied) {
            throw new IllegalArgumentException(
                    "You have already applied for this placement drive!");
        }

        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get applications of a particular student
    public List<Application> getStudentApplications(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
    public void updateApplicationStatus(Long id, String status) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Application not found!"));

        application.setStatus(status);

        applicationRepository.save(application);
    }
    // Dashboard statistics
    public long getTotalApplications() {
        return applicationRepository.count();
    }

    public long getSelectedApplications() {
        return applicationRepository.countByStatus("Selected");
    }

    public long getRejectedApplications() {
        return applicationRepository.countByStatus("Rejected");
    }
    // Get pending applications
    public long getPendingApplications() {
        return applicationRepository.countByStatus("Pending");
    }
}
