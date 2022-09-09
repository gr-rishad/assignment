package com.onnorokompathshala.repo;

import com.onnorokompathshala.entities.VideoReaction;
import com.onnorokompathshala.util.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoReactionRepository extends JpaRepository<VideoReaction,Long> {

    List<VideoReaction> findByReactions(Reaction type);

    // long findTotalVideoReactionByReactions(Reaction reactionType);

    @Query("SELECT COUNT(v) FROM VideoReaction v WHERE v.reactions=:reactionType ")
    long findTotalVideoReaction(@Param("reactionType") Reaction reactionType);
}
