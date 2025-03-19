package elearning.project.models.quizmodel;

import java.util.List;

import elearning.project.models.Assessment;
import elearning.project.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       private String title;
       @OneToMany
       private List<Quizquestions>questions;
       
       @ManyToOne
       @JoinColumn(name="assessmentId")
       Assessment assessment;  
       
}


