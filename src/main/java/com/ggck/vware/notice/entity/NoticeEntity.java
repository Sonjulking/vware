package com.ggck.vware.notice.entity;

import com.ggck.vware.notice_comment.entity.NoticeCommentEntity;
import com.ggck.vware.user.entity.SiteUserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notice_table")
public class NoticeEntity {

  @Id
  @SequenceGenerator(name = "notice_sequence_generator", sequenceName = "notice_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "notice_sequence_generator")
  @Column(unique = true)
  @NotNull
  private Integer postNumber; //게시글 넘버

  @Column(length = 200)
  @NotNull
  private String subject; //게시글 제목

  @Column(columnDefinition = "TEXT")
  @NotNull
  private String content; //게시글 내용

  @NotNull
  private LocalDateTime createTime; //게시글 작성 시간

  private boolean deleteStatus = false; //게시글 삭제 여부
  private LocalDateTime deleteTime; //게시글 삭제 시간

  @ManyToOne
  @NotNull
  private SiteUserEntity author; //게시글 글쓴이

  private LocalDateTime modifyTime; //게시글 수정시간

  @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.REMOVE) //CommentEntity와 연결되어있음.
  private List<NoticeCommentEntity> commentEntityList; //게시글에 달린 댓글

  private int views = 0; //조회수

  private String postType; //게시글 유형

  private int commentCount = 0; //게시글에 달린 댓글 갯수
}
