package com.onnorokompathshala.controllers;

import com.onnorokompathshala.entities.VideoReaction;
import com.onnorokompathshala.entities.VideoShare;
import com.onnorokompathshala.service.VideoReactionService;
import com.onnorokompathshala.service.VideoShareService;
import com.onnorokompathshala.util.Reaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> saveVideo(@RequestParam("file") MultipartFile multipartFile, @RequestParam String uploaderId) throws Exception {

        return videoShareService.saveVideoShare(multipartFile, uploaderId);
    }

    @GetMapping("/getAll")
    public List<VideoShare> getAll() {
        return videoShareService.findAll();
    }

    @GetMapping("/findVideoBy/{upLoaderId}")
    List<VideoShare> findVideoByUploaderId(@PathVariable("upLoaderId") String upLoaderId) {
        return videoShareService.findVideoByFkUserID(upLoaderId);
    }

    @PutMapping("/updateVideoWatch/{videoId}")
    public void updateVideoWatch(@PathVariable("videoId") Long videoId) {
        videoShareService.updateCount(videoId);
    }

    @GetMapping("/getTotalVideoWatchingCount/{videoId}")
    public Long getTotalCount(@PathVariable("videoId") Long videoId) {
        return videoShareService.findTotalWatchingTime(videoId);
    }

    @PostMapping("/reaction/{reactionType}/{reactionBy}/{videoId}")
    public ResponseEntity<?> reaction(@PathVariable Long reactionType, @PathVariable Long reactionBy, @PathVariable Long videoId) {
        return videoReactionService.videoReaction(reactionType, reactionBy, videoId);
    }

    @GetMapping("/findReactionVideoBy/{reactionType}")
    public List<VideoReaction> findReactionByType(@PathVariable("reactionType") Reaction reactionType) {
        return videoReactionService.findReactionBy(reactionType);
    }

    @GetMapping("/findTotalReactionBy/{reactionType}")
    public long findTotalReaction(@PathVariable("reactionType") Reaction reactionType){
        return videoReactionService.findTotalVideoReactionByReactionType(reactionType);
    }

}
