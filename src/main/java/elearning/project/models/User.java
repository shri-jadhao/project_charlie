package elearning.project.models;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

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
    @JsonIgnoreProperties("instructor")
    List<Course> course;
    
    @Column(unique = true)
    private String email;

    private String password;  

    public enum Role {
        STUDENT, INSTRUCTOR
    }
}


