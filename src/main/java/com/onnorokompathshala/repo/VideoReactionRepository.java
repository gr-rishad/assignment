package com.onnorokompathshala.repo;

import com.onnorokompathshala.Dto.DetailsResponseDTO;
import com.onnorokompathshala.entities.VideoReaction;
import com.onnorokompathshala.util.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoReactionRepository extends JpaRepository<VideoReaction,Long> {

    List<VideoReaction> findByReactions(Reaction type);

    VideoReaction findByReactByAndVideoId(String email, Long videoId);

    @Query("SELECT COUNT(v) FROM VideoReaction v WHERE v.reactions=:reactionType AND v.videoId=:videoId ")
    Integer findTotalVideoReaction(@Param("reactionType") Reaction reactionType,@Param("videoId") Long videoId);

    @Query(value = "select a.id,  a.createdBy, group_concat( b.reactions, ':' ,b.reactionDetail) details\n" +
            "from\n" +
            "(select vs.id, vs.video_title, vs.fk_userid, u.user_name as createdBy  from video_share vs, users u where vs.fk_userid = u.email AND vs.id=:videoId) a,\n" +
            "\n" +
            "(select video_id, reactions, concat('[',group_concat('', u.user_name, ''), ']') as reactionDetail\n" +
            "from video_reaction vr, users u where vr.react_by = u.email AND vr.video_id=:videoId\n" +
            "group by reactions,video_id) b\n" +
            "where a.id = b.video_id\n" +
            "group by  id",nativeQuery = true)
    DetailsResponseDTO getVideoDetails(@Param("videoId") Long videoId);
}
