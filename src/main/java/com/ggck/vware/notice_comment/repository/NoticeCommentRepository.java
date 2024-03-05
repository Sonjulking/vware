package com.ggck.vware.notice_comment.repository;

import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.notice_comment.entity.NoticeCommentEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCommentRepository extends JpaRepository<NoticeCommentEntity, Integer> {

  List<NoticeCommentEntity> findByDeleteTimeBefore(LocalDateTime thirtyDaysAgo);
}
