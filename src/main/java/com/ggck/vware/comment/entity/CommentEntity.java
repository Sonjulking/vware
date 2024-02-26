package com.ggck.vware.comment.entity;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.user.entity.SiteUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class CommentEntity {

  @ManyToMany
  Set<SiteUserEntity> voter; //투표

  @Id
  @SequenceGenerator(name = "comment_sequence_generator", sequenceName = "comment_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "comment_sequence_generator")
  @Column(unique = true)
  @NotNull
  private Integer commentNumber; //댓글 넘버

  @Column(columnDefinition = "TEXT")
  @NotNull
  private String content; //댓글 내용

  @NotNull
  private LocalDateTime createTime; //댓글 작성 시간

  private boolean deleteStatus = false; //댓글 삭제 여부
  private LocalDateTime deleteTime;

  @ManyToOne
  @NotNull
  private CommunityPostEntity communityPostEntity; //연결된 게시글 엔티티

  @ManyToOne
  private SiteUserEntity author; //댓글 글쓴이
  private LocalDateTime modifyTime; //댓글 수정시간

}
