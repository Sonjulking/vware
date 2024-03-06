package com.ggck.vware.community_post.controller;

import com.ggck.vware.comment.form.CommentForm;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.form.CommunityPostForm;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.notice.entity.NoticeEntity;
import com.ggck.vware.notice.service.NoticeService;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@Controller
@RequiredArgsConstructor //final이나 @Nonul인 필드 값만 파라미터로 받는 생성자를 만듬.
@RequestMapping("/community")
@EnableScheduling
public class CommunityPostController {

  private final CommunityPostRepository communityPostRepository;
  private final CommunityPostSerivce communityPostSerivce;
  private final NoticeService noticeService;
  private final SiteUserService siteUserService;

  @GetMapping()
  public String communityMain(Model model,
      @RequestParam(value = "page", defaultValue = "1") int page) {
    Page<CommunityPostEntity> paging = this.communityPostSerivce.getList(page - 1);
    List<NoticeEntity> noticeList = this.noticeService.getNoticeList();
    model.addAttribute("paging", paging);
    model.addAttribute("noticeList", noticeList);
    return "community/community";
  }

  @GetMapping(value = "/detail/{id}")
  public String postDetail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {

    CommunityPostEntity communityPostEntity = this.communityPostSerivce.getPost(id);

    model.addAttribute("postDetail", communityPostEntity);

    return "community/post_view";

  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/create")
  public String postCreate(CommunityPostForm communityPostForm) {

    return "community/post_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create")
  public String postCreate(@Valid CommunityPostForm communityPostForm,
      BindingResult bindingResult, Principal principal, @RequestParam("postType") String postType) {

    if (bindingResult.hasErrors()) {
      return "community/post_form";
    }

    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());

    this.communityPostSerivce.create(communityPostForm.getSubject(),
        communityPostForm.getContent(), siteUser, postType);

    return "redirect:/community"; // 질문 저장후 질문목록으로 이동

  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String postModify(CommunityPostForm communityPostForm, @PathVariable("id") Integer id,
      Principal principal) {

    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(id);
    if (!communityPost.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
    }
    communityPostForm.setSubject(communityPost.getSubject());
    communityPostForm.setContent(communityPost.getContent());

    return "community/post_form";

  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String postModify(@Valid CommunityPostForm communityPostForm, BindingResult bindingResult,
      Principal principal, @PathVariable("id") Integer id) {
    if (bindingResult.hasErrors()) {
      return "community/post_form";
    }
    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(id);
    if (!communityPost.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
    }

    this.communityPostSerivce.modify(communityPost, communityPostForm.getSubject(),
        communityPostForm.getContent());

    return String.format("redirect:/community/detail/%s", id);

  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String postDelete(Principal principal, @PathVariable("id") Integer id) {
    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(id);
    if (!communityPost.getAuthor().getUserId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
    }
    this.communityPostSerivce.delete(communityPost);
    return "redirect:/";
  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  //@Scheduled(cron = "0/10 * * * * *")//10초마다 실행
  public void cleanDeleteData() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    List<CommunityPostEntity> deleteCommunityPostList = communityPostRepository.findByDeleteTimeBefore(
        thirtyDaysAgo); //삭제한지 30일이 지난 게시글
    communityPostRepository.deleteAll(deleteCommunityPostList);
    System.out.println("게시글 자동삭제됐다옹 : " + deleteCommunityPostList);
  }

}
