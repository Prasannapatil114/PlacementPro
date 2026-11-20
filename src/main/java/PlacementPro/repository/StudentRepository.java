
package PlacementPro.repository;

import PlacementPro.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Check whether email is already registered
    boolean existsByEmail(String email);

    // Find student by email
    Optional<Student> findByEmail(String email);

    // Search students by name
    List<Student> findByNameContainingIgnoreCase(String name);

    // Filter students by branch
    List<Student> findByBranchIgnoreCase(String branch);

    // Count students branch-wise
    @Query("SELECT s.branch, COUNT(s) FROM Student s GROUP BY s.branch")
    List<Object[]> countStudentsByBranch();
}
