package com.ggck.vware.comment.controller;

import com.ggck.vware.comment.form.CommentForm;
import com.ggck.vware.comment.service.CommentService;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller

public class CommentController {

  private final CommunityPostSerivce communityPostSerivce;
  private final CommentService commentService;
  private final SiteUserService siteUserService;

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

}
