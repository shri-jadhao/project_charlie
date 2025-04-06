package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"enrollments","assessment"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseID;
    
    @Column(unique = true,nullable = false)
    @NotEmpty(message = "Course title must not be empty!")
    private String title;
    private String description; 
    
    @ManyToOne
    User instructor; 
    
    @OneToMany(mappedBy="course")
    List<Enrollment> enrollments;

    // all operations on course will be cascaded to Video entity
    @OneToMany(mappedBy="course",cascade = CascadeType.ALL)
    List<Video> video;
    
    @OneToMany(mappedBy = "course")
    List<Assessment> assessment;
   
}
