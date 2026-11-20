
package PlacementPro.service;

import PlacementPro.entity.Student;
import PlacementPro.repository.StudentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Student Registration
    public Student registerStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }

        String encryptedPassword =
                passwordEncoder.encode(student.getPassword());

        student.setPassword(encryptedPassword);

        return studentRepository.save(student);
    }

    // Student Login
    public Student loginStudent(String email, String password) {

        Student student = studentRepository.findByEmail(email)
                .orElse(null);

        if (student != null &&
                passwordEncoder.matches(password, student.getPassword())) {

            return student;
        }

        return null;
    }

    // Update Student Profile
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
    // Get total registered students
    public long getTotalStudents() {
        return studentRepository.count();
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    // Search students by name
    public List<Student> searchStudents(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // Filter students by branch
    public List<Student> filterStudentsByBranch(String branch) {
        return studentRepository.findByBranchIgnoreCase(branch);
    }
    // Get student count branch-wise
    public List<Object[]> getStudentsByBranch() {
        return studentRepository.countStudentsByBranch();
    }
}
