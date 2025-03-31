package elearning.project.modelDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizDTO {
        private Long id;
        private String title;
        private List<Integer>questionId;
        private Long assessmentId;
}
