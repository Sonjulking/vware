package com.ggck.vware.comment.repository;

import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

  List<CommentEntity> findByDeleteTimeBefore(LocalDateTime thirtyDaysAgo);
}
