package com.ggck.vware.notice_comment.contorller;

import com.ggck.vware.comment.form.CommentForm;
import com.ggck.vware.notice.entity.NoticeEntity;
import com.ggck.vware.notice.service.NoticeService;
import com.ggck.vware.notice_comment.entity.NoticeCommentEntity;
import com.ggck.vware.notice_comment.repository.NoticeCommentRepository;
import com.ggck.vware.notice_comment.service.NoticeCommentService;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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

@RequestMapping("/notice/comment")
@RequiredArgsConstructor
@Controller
@EnableScheduling
public class NoticeCommentController {

  private final NoticeService noticeService;
  private final NoticeCommentRepository noticeCommentRepository;
  private final NoticeCommentService noticeCommentService;
  private final SiteUserService siteUserService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create/{id}")
  public String createComment(@Valid CommentForm commentForm, BindingResult bindingResult,
      Model model, @PathVariable("id") Integer id
      , Principal principal) {
    NoticeEntity notice = this.noticeService.getPost(id);
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());

    if (bindingResult.hasErrors()) {
      model.addAttribute("postDetail", notice);
      return "notice/notice_view";

    }

    this.noticeCommentService.create(notice, commentForm.getContent(), siteUser);
    return String.format("redirect:/notice/detail/%s", id);

  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
      @PathVariable("id") Integer id, Principal principal, Model model, @RequestParam("postNumber")
  Integer postNumber) {
    NoticeEntity notice = this.noticeService.getPost(postNumber);//postNumber
    if (bindingResult.hasErrors()) {
      model.addAttribute("postDetail", notice);
      return "notice/notice_view";
    }
    NoticeCommentEntity noticeComment = this.noticeCommentService.getComment(id);
    if (!noticeComment.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.noticeCommentService.modify(noticeComment, commentForm.getContent());
    return String.format("redirect:/notice/detail/%s",
        noticeComment.getNoticeEntity().getPostNumber());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
    NoticeCommentEntity noticeComment = this.noticeCommentService.getComment(id);
    if (!noticeComment.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
    }
    this.noticeCommentService.delete(noticeComment);
    return String.format("redirect:/notice/detail/%s",
        noticeComment.getNoticeEntity().getPostNumber());
  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  //@Scheduled(cron = "0/10 * * * * *")//10초마다 실행
  public void cleanDeleteData() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    List<NoticeCommentEntity> noticeCommentList = noticeCommentRepository.findByDeleteTimeBefore(
        thirtyDaysAgo); //삭제한지 30일이 지난 게시글
    noticeCommentRepository.deleteAll(noticeCommentList);
    System.out.println("공지 댓글 자동삭제됐다옹 : " + noticeCommentList);
  }


}
