package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
   
    @OneToMany(mappedBy = "instructor")
    @JsonIgnore
    List<Course> course;
    
    @Column(unique = true)
    private String email;

    private String password;  

    public enum Role {
        STUDENT, INSTRUCTOR
    }
}


