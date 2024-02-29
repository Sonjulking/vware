package com.ggck.vware.community_post.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommunityPostSerivce {

  private final CommunityPostRepository communityPostRepository;

  public CommunityPostEntity getPost(Integer id) {
    Optional<CommunityPostEntity> communityPostEntity = this.communityPostRepository.findById(id);
    if (communityPostEntity.isPresent()) {
      return communityPostEntity.get();
    } else {
      throw new DataNotFoundException("post not found");
    }
  }

}
