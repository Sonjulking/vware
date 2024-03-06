package com.ggck.vware.community_post.repository;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.user.entity.SiteUserEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityPostRepository extends JpaRepository<CommunityPostEntity, Integer> {

  Page<CommunityPostEntity> findAll(Pageable pageable);

/*  List<CommunityPostEntity> findByPostTypeAndDeleteStatusIsFalseOrderByCreateTimeDesc(String postType,
      Pageable pageable);*/

  @Query("select cp from CommunityPostEntity cp where cp.deleteStatus != true")
  Page<CommunityPostEntity> findAllNotDeleted(Pageable pageable);

  List<CommunityPostEntity> findByDeleteTimeBefore(LocalDateTime date);

  //@Query("select cp from CommunityPostEntity  cp left join fetch cp.commentEntityList c where cp.postNumber = :postId AND c.deleteStatus = false")
  //@Query("SELECT cp FROM CommunityPostEntity cp LEFT JOIN FETCH cp.commentEntityList c WHERE cp.postNumber = :postId AND (c.deleteStatus IS NULL OR c.deleteStatus = false)")

  //@Query("SELECT cp FROM CommunityPostEntity cp WHERE cp.postNumber = :postId")
  // left join fetch는 join과 유사함. join은 쿼리결과에 대상 엔티티만 가져오지만, fetch가 붙으면 대상엔티티와 연관 엔티티들을 함께 가져옴.
  // :postid 는 변수명임 Integer id가 들어가는
  //Optional<CommunityPostEntity> findPostWithCommentNotDeleted(@Param("postId") Integer id);

}
