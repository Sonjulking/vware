package com.ggck.vware.community_post.repository;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPostEntity, Integer> {

}
