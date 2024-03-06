package com.ggck.vware.community_post.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommunityPostSerivce {

  private final CommunityPostRepository communityPostRepository;

  public CommunityPostEntity getPost(Integer id) {
    Optional<CommunityPostEntity> communityPostEntity = this.communityPostRepository.findById(id);
    if (communityPostEntity.isPresent()) {
      CommunityPostEntity communityPost = communityPostEntity.get();

      communityPost.setViews(communityPost.getViews() + 1); //조회수 증가
      this.communityPostRepository.save(communityPost);

      return communityPost;

    } else {
      throw new DataNotFoundException("post not found");
    }
  }

  public void create(String subject, String content, SiteUserEntity siteUser, String postType) {

    CommunityPostEntity cp = new CommunityPostEntity();
    cp.setSubject(subject);
    cp.setContent(content);
    cp.setAuthor(siteUser);
    cp.setPostType(postType);
    cp.setCreateTime(LocalDateTime.now());
    this.communityPostRepository.save(cp);

  }

  public void create(String subject, String content, SiteUserEntity user) {

    CommunityPostEntity cp = new CommunityPostEntity();
    cp.setSubject(subject);
    cp.setContent(content);
    cp.setCreateTime(LocalDateTime.now());
    cp.setAuthor(user);
    this.communityPostRepository.save(cp);

  }

  public Page<CommunityPostEntity> getList(int page) {
    List<Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createTime")); //최신순으로 정렬
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); //페이지당, 10개의 게시물을 보여줌

    return this.communityPostRepository.findAllNotDeleted(pageable);
    //return this.communityPostRepository.findAll(pageable);
  }

  public void modify(CommunityPostEntity communityPost, String subject, String content) {
    communityPost.setSubject(subject);
    communityPost.setContent(content);
    communityPost.setModifyTime(LocalDateTime.now());
    this.communityPostRepository.save(communityPost);

  }

  public void delete(CommunityPostEntity communityPost) {
    communityPost.setDeleteStatus(true);
    communityPost.setDeleteTime(LocalDateTime.now());
    this.communityPostRepository.save(communityPost);
    //this.communityPostRepository.delete(communityPost);
  }

/*
  public void flagDelete(CommunityPostEntity communityPost) {
    communityPost.setDeleteStatus(true);
    communityPost.setDeleteTime(LocalDateTime.now());
    this.communityPostRepository.save(communityPost);
  }
*/

//  public List<CommunityPostEntity> getNoticeList() {
//    PageRequest pageRequest = PageRequest.of(0, 3); // 최신순으로 3개
//    return this.communityPostRepository.findByPostTypeAndDeleteStatusIsFalseOrderByCreateTimeDesc(
//        "공지", pageRequest);
//  }
}
