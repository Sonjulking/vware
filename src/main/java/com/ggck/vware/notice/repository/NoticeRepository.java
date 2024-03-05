package com.ggck.vware.notice.repository;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.notice.entity.NoticeEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {

  Page<NoticeEntity> findAll(Pageable pageable);

  List<NoticeEntity> findByPostTypeAndDeleteStatusIsFalseOrderByCreateTimeDesc(String postType,
      Pageable pageable);

  @Query("select nt from NoticeEntity nt where nt.deleteStatus != true")
  Page<NoticeEntity> findAllNotDeleted(Pageable pageable);

  List<NoticeEntity> findByDeleteTimeBefore(LocalDateTime date);
}
