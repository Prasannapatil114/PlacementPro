
package PlacementPro.repository;

import PlacementPro.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    List<Application> findByStudentId(Long studentId);

    boolean existsByStudentIdAndPlacementDriveId(
            Long studentId, Long driveId);
    long count();

    long countByStatus(String status);
}
