package com.ggck.vware.notice.controller;

import com.ggck.vware.comment.form.CommentForm;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.form.CommunityPostForm;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.notice.entity.NoticeEntity;
import com.ggck.vware.notice.repository.NoticeRepository;
import com.ggck.vware.notice.service.NoticeSerivce;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeController {

  private final NoticeSerivce noticeSerivce;
  private final NoticeRepository noticeRepository;
  private final SiteUserService siteUserService;


  @GetMapping()
  public String noticeList(Model model,
      @RequestParam(value = "page", defaultValue = "1") int page) {
    Page<NoticeEntity> paging = this.noticeSerivce.getList(page - 1);
    model.addAttribute("paging", paging);
    return "notice/notice_list";

  }


  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Secured("ROLE_ADMIN")
  @GetMapping("/posting")
  public String noticePosting(CommunityPostForm communityPostForm) {
    return "notice/notice_form";

  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Secured("ROLE_ADMIN")
  @PostMapping("/posting")
  public String noticePosting(@Valid CommunityPostForm communityPostForm,
      BindingResult bindingResult, Principal principal, @RequestParam("postType") String postType) {

    if (bindingResult.hasErrors()) {
      return "notice/notice_form";
    }

    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());

    this.noticeSerivce.create(communityPostForm.getSubject(),
        communityPostForm.getContent(), siteUser, postType);

    return "redirect:/notice"; // 질문 저장후 질문목록으로 이동

  }

  @GetMapping(value = "/detail/{id}")
  public String postDetail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {

    NoticeEntity noticeEntity = this.noticeSerivce.getPost(id);
    model.addAttribute("postDetail", noticeEntity);

    return "notice/notice_view";

  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String postDelete(Principal principal, @PathVariable("id") Integer id) {
    NoticeEntity notice = this.noticeSerivce.getPost(id);
    if (!notice.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
    }
    this.noticeSerivce.delete(notice);
    return "redirect:/";
  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  public void cleanDeleteData() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    List<NoticeEntity> deleteNoticeList = noticeRepository.findByDeleteTimeBefore(
        thirtyDaysAgo); //삭제한지 30일이 지난 게시글
    noticeRepository.deleteAll(deleteNoticeList);
    System.out.println("공지 자동삭제됐다옹 : " + deleteNoticeList);
  }

}
