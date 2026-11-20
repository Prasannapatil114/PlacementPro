
package PlacementPro.repository;

import PlacementPro.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementDriveRepository
        extends JpaRepository<PlacementDrive, Long> {
    long count();

}
