package elearning.project.services;

import elearning.project.models.Video;
import elearning.project.repositories.VideoRepo;
import elearning.project.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepo videoRepository;

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public Video getVideoById(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.orElse(null);
    }

    @Override
    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Long id, Video video) {
        if (videoRepository.existsById(id)) {
            video.setVidoeid(id);
            return videoRepository.save(video);
        }
        return null;
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}