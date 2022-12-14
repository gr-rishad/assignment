package com.onnorokompathshala.entities;

import com.onnorokompathshala.util.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video_reaction")
public class VideoReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Reaction reactions;
    private Long videoId;
    private String reactBy;
    private LocalDateTime reactionTime;
}
