package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseID;
    private String title;
    private String description;
    private String contentURL;
    
    @ManyToOne
    @JsonIgnoreProperties("course")
    User instructor; 
    
    @OneToMany(mappedBy="course")
    @JsonIgnoreProperties(value="course")
    List<Enrollment> enrollments;
    
    @OneToMany(mappedBy="course",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("course")
    List<Video> video;
    
    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    List<Assessment> assessment;
   
}
