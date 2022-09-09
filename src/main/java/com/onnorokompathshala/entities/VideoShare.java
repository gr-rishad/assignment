package com.onnorokompathshala.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video_share")
public class VideoShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fkUserID;
    private String videoUrl;
    private String videoTitle;
    private Integer totView;
    private LocalDateTime uploadedTime;
}
