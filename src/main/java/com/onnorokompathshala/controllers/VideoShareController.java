package com.onnorokompathshala.controllers;

import com.onnorokompathshala.Dto.DetailsResponseDTO;
import com.onnorokompathshala.entities.VideoReaction;
import com.onnorokompathshala.entities.VideoShare;
import com.onnorokompathshala.service.VideoReactionService;
import com.onnorokompathshala.service.VideoShareService;
import com.onnorokompathshala.util.Reaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/videoShareController")
public class VideoShareController {

    private VideoShareService videoShareService;
    private VideoReactionService videoReactionService;

    public VideoShareController(VideoShareService videoShareService, VideoReactionService videoReactionService) {
        this.videoShareService = videoShareService;
        this.videoReactionService = videoReactionService;
    }


    @PostMapping("/saveVideo")
    public ResponseEntity<?> saveVideo(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        String userEmail = principal.getName();
        return videoShareService.saveVideoShare(file, userEmail);
    }
    @GetMapping("/getAllVideos")
    public List<VideoShare> getAll() {
        return videoShareService.findAll();
    }


    @GetMapping("/findVideoByUpLoaderId")
    List<VideoShare> findVideoByUploaderId(Principal principal) {
        String userEmail=principal.getName();
        return videoShareService.findVideoByFkUserID(userEmail);
    }

    @PutMapping("/videoWatch/{videoId}")
    public void updateVideoWatch(@PathVariable("videoId") Long videoId) {
        videoShareService.updateCount(videoId);
    }

    @GetMapping("/getTotalVideoWatchingCount/{videoId}")
    public Integer getTotalCount(@PathVariable("videoId") Integer videoId) {
        return videoShareService.findTotalWatchingTime(videoId);
    }

    @PostMapping("/reaction")
    public ResponseEntity<?> reaction(@RequestBody VideoReaction videoReact) {
        return videoReactionService.videoReaction(videoReact);
    }

    @GetMapping("/findReactionVideoBy/{reactionType}")
    public List<VideoReaction> findReactionByType(@PathVariable("reactionType") Reaction reactionType) {
        return videoReactionService.findReactionBy(reactionType);
    }

    @GetMapping("/findTotalReactionBy/{reactionType}/{videoId}")
    public long findTotalReaction(@PathVariable("reactionType") Reaction reactionType, @PathVariable("videoId") Long videoId) {
        return videoReactionService.findTotalVideoReactionByReactionType(reactionType, videoId);
    }

    @GetMapping("/findVideoDetailsBy/{videoId}")
    public DetailsResponseDTO getVideoDetails(@PathVariable("videoId")Long videoId ){
        return videoReactionService.getVideoDetails(videoId);
    }

}
