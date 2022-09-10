package com.onnorokompathshala.service;

import com.onnorokompathshala.Dto.DetailsResponseDTO;
import com.onnorokompathshala.entities.User;
import com.onnorokompathshala.entities.VideoReaction;
import com.onnorokompathshala.entities.VideoShare;
import com.onnorokompathshala.repo.VideoReactionRepository;
import com.onnorokompathshala.repo.VideoShareRepository;
import com.onnorokompathshala.util.Reaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VideoReactionService {

    private VideoReactionRepository videoReactionRepository;
    private VideoShareRepository videoShareRepository;
    private UserService userService;

    public VideoReactionService(VideoReactionRepository videoReactionRepository, VideoShareRepository videoShareRepository, UserService userService) {
        this.videoReactionRepository = videoReactionRepository;
        this.videoShareRepository = videoShareRepository;
        this.userService = userService;
    }


    public ResponseEntity<?> videoReaction(VideoReaction videoReact) {

        VideoReaction reaction = new VideoReaction();

        if (videoReact != null) {
            VideoReaction videoReaction = videoReactionRepository.findByReactByAndVideoId(videoReact.getReactBy(), videoReact.getVideoId());
            if (videoReaction != null) {
                videoReaction.setReactions(videoReact.getReactions());
                videoReaction.setReactionTime(LocalDateTime.now());
                videoReactionRepository.save(videoReaction);
            } else {
                reaction.setReactionTime(LocalDateTime.now());
                reaction.setReactBy(videoReact.getReactBy());
                reaction.setVideoId(videoReact.getVideoId());

                reaction.setReactions(videoReact.getReactions());
                videoReactionRepository.save(reaction);
            }
        }
        return ResponseEntity.ok("Reaction Saved");
    }

    public List<VideoReaction> findReactionBy(Reaction type) {
        return videoReactionRepository.findByReactions(type);
    }

    public Integer findTotalVideoReactionByReactionType(Reaction reactionType, Long videoId) {
        return videoReactionRepository.findTotalVideoReaction(reactionType, videoId);
    }

    public DetailsResponseDTO getVideoDetails(Long videoId){
        return videoReactionRepository.getVideoDetails(videoId);
    }
}
