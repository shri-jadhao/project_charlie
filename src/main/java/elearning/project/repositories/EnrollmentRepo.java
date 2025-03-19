package elearning.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import elearning.project.models.Enrollment;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

}
