package com.ggck.vware.notice.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import com.ggck.vware.notice.entity.NoticeEntity;
import com.ggck.vware.notice.repository.NoticeRepository;
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
public class NoticeSerivce {

  private final NoticeRepository noticeRepository;

  public NoticeEntity getPost(Integer id) {
    Optional<NoticeEntity> noticeEntity = this.noticeRepository.findById(id);
    if (noticeEntity.isPresent()) {
      return noticeEntity.get();
    } else {
      throw new DataNotFoundException("post not found");
    }
  }

  public void create(String subject, String content, SiteUserEntity siteUser, String postType) {

    NoticeEntity nt = new NoticeEntity();
    nt.setSubject(subject);
    nt.setContent(content);
    nt.setAuthor(siteUser);
    nt.setPostType(postType);
    nt.setCreateTime(LocalDateTime.now());
    this.noticeRepository.save(nt);

  }

  public void create(String subject, String content, SiteUserEntity siteUser) {

    NoticeEntity nt = new NoticeEntity();
    nt.setSubject(subject);
    nt.setContent(content);
    nt.setAuthor(siteUser);
    nt.setCreateTime(LocalDateTime.now());
    this.noticeRepository.save(nt);

  }

  public Page<NoticeEntity> getList(int page) {
    List<Order> sorts = new ArrayList<>();
    sorts.add(Order.desc("createTime")); //최신순으로 정렬
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); //페이지당, 10개의 게시물을 보여줌
    return this.noticeRepository.findAllNotDeleted(pageable);
    //return this.noticeRepository.findAll(pageable);
  }

  public void modify(NoticeEntity noticeEntity, String subject, String content) {
    noticeEntity.setSubject(subject);
    noticeEntity.setContent(content);
    noticeEntity.setModifyTime(LocalDateTime.now());
    this.noticeRepository.save(noticeEntity);

  }

  public void delete(NoticeEntity noticeEntity) {
    noticeEntity.setDeleteStatus(true);
    noticeEntity.setDeleteTime(LocalDateTime.now());
    this.noticeRepository.save(noticeEntity);
    // this.noticeRepository.delete(noticeEntity);
  }


  public List<NoticeEntity> getNoticeList() {
    PageRequest pageRequest = PageRequest.of(0, 3); // 최신순으로 3개
    return this.noticeRepository.findByPostTypeAndDeleteStatusIsFalseOrderByCreateTimeDesc("공지",
        pageRequest); //공지라고 적혀있는 로우
  }
}
