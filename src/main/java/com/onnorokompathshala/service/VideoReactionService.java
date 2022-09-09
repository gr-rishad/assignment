package com.onnorokompathshala.service;

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


    public ResponseEntity<?> videoReaction(Long reactionType, Long reactionBy, Long videoId) {
        VideoReaction reaction = new VideoReaction();
        VideoShare videoShare = videoShareRepository.findVideoShareById(videoId).get();
        User user = userService.findByUserId(reactionBy).get();
        if (!reactionType.equals(null)) {
            Optional<VideoReaction> videoReaction = videoReactionRepository.findById(videoId);
            if(videoReaction.isPresent()){
                if (videoReaction.get().getReactions().equals("LIKE")) {
                    reaction.setReactions(Reaction.UNLIKE);
                } else {
                    reaction.setReactions(Reaction.LIKE);
                }
            }else {
                if (videoReaction.get().getReactions().equals("LIKE")) {
                    reaction.setReactions(Reaction.LIKE);
                } else {
                    reaction.setReactions(Reaction.UNLIKE);
                }
            }

//            if (videoReaction.getReactions().equals("LIKE")) {
//                reaction.setReactions(Reaction.UNLIKE);
//            } else {
//                reaction.setReactions(Reaction.LIKE);
//            }
        }

        reaction.setReactionTime(LocalDateTime.now());
        reaction.setReactionsBy(user);
        reaction.setVideoId(videoShare);
        videoReactionRepository.save(reaction);

        return ResponseEntity.ok("Reaction Saved");
    }

    public List<VideoReaction> findReactionBy(Reaction type) {
        return videoReactionRepository.findByReactions(type);
    }

    public long findTotalVideoReactionByReactionType(Reaction reactionType) {
        return videoReactionRepository.findTotalVideoReaction(reactionType);
    }
}
