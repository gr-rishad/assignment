package com.onnorokompathshala.repo;

import com.onnorokompathshala.entities.VideoShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VideoShareRepository extends JpaRepository<VideoShare, Long> {

    List<VideoShare> findVideoByFkUserID(String id);

    Optional<VideoShare> findVideoShareById(Long id);

    long findByTotView(Long videoId);

    @Transactional
    @Modifying
    @Query("UPDATE VideoShare  v SET v.totView=v.totView+1 WHERE v.id=:id")
    void count(@Param("id") Long id);
}
