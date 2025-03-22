package elearning.project.services;

import elearning.project.models.Video;
import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();
    Video getVideoById(Long id);
    Video createVideo(Video video);
    Video updateVideo(Long id, Video video);
    void deleteVideo(Long id);
}
