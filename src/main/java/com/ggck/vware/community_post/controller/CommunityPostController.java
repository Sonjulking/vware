package com.ggck.vware.community_post.controller;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.form.CommunityPostForm;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor //final이나 @Nonul인 필드 값만 파라미터로 받는 생성자를 만듬.
@RequestMapping("/community")
public class CommunityPostController {

  private final CommunityPostRepository communityPostRepository;
  private final CommunityPostSerivce communityPostSerivce;
  private final SiteUserService siteUserService;

  @GetMapping()
  public String communityMain(Model model,
      @RequestParam(value = "page", defaultValue = "1") int page) {
    Page<CommunityPostEntity> paging = this.communityPostSerivce.getList(page - 1);
    model.addAttribute("paging", paging);
    return "community/community";
  }

  @GetMapping(value = "/detail/{id}")
  public String postDetail(Model model, @PathVariable("id") Integer id) {

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

}
