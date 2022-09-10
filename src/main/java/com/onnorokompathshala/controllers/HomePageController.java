package com.onnorokompathshala.controllers;


import com.onnorokompathshala.entities.VideoShare;
import com.onnorokompathshala.service.VideoShareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homePageController")
public class HomePageController {

    private VideoShareService videoShareService;

    public HomePageController(VideoShareService videoShareService) {
        this.videoShareService = videoShareService;
    }

    @GetMapping("/getAllVideos")
    public List<VideoShare> getAll() {
        return videoShareService.findAll();
    }

    @PutMapping("/videoWatchCount/{videoId}")
    public void updateVideoWatch(@PathVariable("videoId") Long videoId) {
        videoShareService.updateCount(videoId);
    }
}
