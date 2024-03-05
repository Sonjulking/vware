package com.ggck.vware.comment.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.user.entity.SiteUserEntity;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;

  public CommentEntity create(CommunityPostEntity communityPost, String content,
      SiteUserEntity author) {
    CommentEntity comment = new CommentEntity();
    comment.setContent(content);
    comment.setCreateTime(LocalDateTime.now());
    comment.setCommunityPostEntity(communityPost);
    comment.setAuthor(author);
    this.commentRepository.save(comment);
    return comment;
  }

  public CommentEntity getComment(Integer id) {
    Optional<CommentEntity> comment = this.commentRepository.findById(id);
    if (comment.isPresent()) {
      return comment.get();
    } else {
      throw new DataNotFoundException("comment not found");
    }
  }

  public void modify(CommentEntity comment, String content) {
    comment.setContent(content);
    comment.setModifyTime(LocalDateTime.now());
    this.commentRepository.save(comment);
  }

  public void delete(CommentEntity comment) {
    comment.setDeleteStatus(true);
    comment.setDeleteTime(LocalDateTime.now());
    this.commentRepository.save(comment);
    //this.commentRepository.delete(comment);
  }

}
