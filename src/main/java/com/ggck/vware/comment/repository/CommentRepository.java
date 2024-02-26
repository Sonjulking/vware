package com.ggck.vware.comment.repository;

import com.ggck.vware.comment.entity.CommentEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
