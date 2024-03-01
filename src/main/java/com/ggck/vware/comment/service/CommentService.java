package com.ggck.vware.comment.service;

import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDateTime;
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
}
