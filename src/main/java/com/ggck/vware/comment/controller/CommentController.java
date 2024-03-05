package com.ggck.vware.comment.controller;

import com.ggck.vware.comment.entity.CommentEntity;
import com.ggck.vware.comment.form.CommentForm;
import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.comment.service.CommentService;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
@EnableScheduling
public class CommentController {

  private final CommunityPostSerivce communityPostSerivce;
  private final CommentRepository commentRepository;
  private final CommentService commentService;
  private final SiteUserService siteUserService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create/{id}")
  public String createComment(@Valid CommentForm commentForm, BindingResult bindingResult,
      Model model, @PathVariable("id") Integer id
      , Principal principal) {
    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(id);
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());

    if (bindingResult.hasErrors()) {
      model.addAttribute("postDetail", communityPost);
      return "community/post_view";

    }

    this.commentService.create(communityPost, commentForm.getContent(), siteUser);
    return String.format("redirect:/community/detail/%s", id);

  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
      @PathVariable("id") Integer id, Principal principal, Model model, @RequestParam("postNumber")
  Integer postNumber) {
    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(postNumber); //postNumber
    if (bindingResult.hasErrors()) {
      model.addAttribute("postDetail", communityPost);
      return "community/post_view";
    }
    CommentEntity comment = this.commentService.getComment(id);
    if (!comment.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.commentService.modify(comment, commentForm.getContent());
    return String.format("redirect:/community/detail/%s",
        comment.getCommunityPostEntity().getPostNumber());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
    CommentEntity comment = this.commentService.getComment(id);
    if (!comment.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
    }
    this.commentService.delete(comment);
    return String.format("redirect:/community/detail/%s",
        comment.getCommunityPostEntity().getPostNumber());
  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  //@Scheduled(cron = "0/10 * * * * *")//10초마다 실행
  public void cleanDeleteData() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    List<CommentEntity> deleteCommentList = commentRepository.findByDeleteTimeBefore(
        thirtyDaysAgo); //삭제한지 30일이 지난 게시글
    commentRepository.deleteAll(deleteCommentList);
    System.out.println("댓글 자동삭제됐다옹 : " + deleteCommentList);
  }

}
