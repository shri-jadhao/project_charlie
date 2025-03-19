package elearning.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elearning.project.models.Video;

@Repository
public interface VideoRepo extends JpaRepository<Video, Long>{
     
}
