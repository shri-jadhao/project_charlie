package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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
