package com.ggck.vware.notice_comment.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.notice.entity.NoticeEntity;
import com.ggck.vware.notice_comment.entity.NoticeCommentEntity;
import com.ggck.vware.notice_comment.repository.NoticeCommentRepository;
import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCommentService {

  private final NoticeCommentRepository noticeCommentRepository;

  public NoticeCommentEntity create(NoticeEntity noticeEntity, String content,
      SiteUserEntity author) {
    NoticeCommentEntity comment = new NoticeCommentEntity();
    comment.setContent(content);
    comment.setCreateTime(LocalDateTime.now());
    comment.setNoticeEntity(noticeEntity);
    comment.setAuthor(author);
    this.noticeCommentRepository.save(comment);
    return comment;
  }

  public NoticeCommentEntity getComment(Integer id) {
    Optional<NoticeCommentEntity> comment = this.noticeCommentRepository.findById(id);
    if (comment.isPresent()) {
      return comment.get();
    } else {
      throw new DataNotFoundException("comment not found");
    }
  }

  public void modify(NoticeCommentEntity noticeCommentEntity, String content) {
    noticeCommentEntity.setContent(content);
    noticeCommentEntity.setModifyTime(LocalDateTime.now());
    this.noticeCommentRepository.save(noticeCommentEntity);
  }

  public void delete(NoticeCommentEntity noticeComment) {

    noticeComment.setDeleteStatus(true);
    noticeComment.setDeleteTime(LocalDateTime.now());
    this.noticeCommentRepository.save(noticeComment);
    //this.noticeCommentRepository.delete(noticeComment);
  }

}
