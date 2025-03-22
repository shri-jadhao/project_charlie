package elearning.project.services;

import elearning.project.models.Video;
import elearning.project.repositories.VideoRepo;
import elearning.project.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideoRepo videoRepository;

    @Override
    public List<Video> getAllVideos() {
        logger.info("Fetching all videos");
        return videoRepository.findAll();
    }

    @Override
    public Video getVideoById(Long id) {
        logger.info("Fetching video with ID: {}", id);
        Optional<Video> video = videoRepository.findById(id);
        return video.orElse(null);
    }

    @Override
    public Video createVideo(Video video) {
        logger.info("Creating video with ID: {}", video.getVidoeid());
        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Long id, Video video) {
        logger.info("Updating video with ID: {}", id);
        if (videoRepository.existsById(id)) {
            video.setVidoeid(id);
            return videoRepository.save(video);
        }
        logger.warn("Video with ID: {} not found", id);
        return null;
    }

    @Override
    public void deleteVideo(Long id) {
        logger.info("Deleting video with ID: {}", id);
        videoRepository.deleteById(id);
    }
}