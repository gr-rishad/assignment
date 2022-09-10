package com.onnorokompathshala.service;

import com.onnorokompathshala.entities.VideoShare;
import com.onnorokompathshala.repo.VideoShareRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.onnorokompathshala.constant.PathConstant.DEFAULT_USER_IMAGE_PATH;

@Service
public class VideoShareService {

    private VideoShareRepository videoShareRepository;

    public VideoShareService(VideoShareRepository videoShareRepository) {
        this.videoShareRepository = videoShareRepository;
    }

    public ResponseEntity<?> saveVideoShare(MultipartFile multipartFile, String id) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileName = fileName.trim();

        VideoShare vShare = new VideoShare();
        vShare.setFkUserID(id);
        vShare.setTotView(0);
        vShare.setUploadedTime(LocalDateTime.now());
        vShare.setVideoTitle(fileName);

        File path = new File(DEFAULT_USER_IMAGE_PATH + fileName);
        path.createNewFile();
        FileOutputStream output = new FileOutputStream(path);
        output.write(multipartFile.getBytes());
        output.close();

        vShare.setVideoUrl(DEFAULT_USER_IMAGE_PATH + fileName);

        videoShareRepository.save(vShare);
        return ResponseEntity.ok("Saved");
    }

    public List<VideoShare> findAll() {
        return videoShareRepository.findAll();
    }

    public List<VideoShare> findVideoByFkUserID(String uploaderId) {
        return videoShareRepository.findVideoByFkUserID(uploaderId);
    }

    public void updateCount(Long videoId) {
        videoShareRepository.count(videoId);
    }

    public Integer findTotalWatchingTime(Integer videoId) {
        return videoShareRepository.findTotView(videoId);
    }

    public Optional<VideoShare> findVideoById(Long id) {
        return videoShareRepository.findVideoShareById(id);
    }
}
