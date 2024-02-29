package com.ggck.vware.comment.service;

import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;

  public void create(CommunityPostEntity communityPost, String content) {
    CommentEntity comment = new CommentEntity();
    comment.setContent(content);
    comment.setCreateTime(LocalDateTime.now());

    comment.setCommunityPostEntity(communityPost);
    this.commentRepository.save(comment);
  }
}
