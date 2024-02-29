package com.ggck.vware.comment.controller;

import com.ggck.vware.comment.service.CommentService;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

  private final CommunityPostSerivce communityPostSerivce;
  private final CommentService commentService;

  @PostMapping("/create/{id}")
  public String createComment(Model model, @PathVariable("id") Integer id,
      @RequestParam(value = "content") String content) {

    CommunityPostEntity communityPost = this.communityPostSerivce.getPost(id);
    this.commentService.create(communityPost, content);
    return String.format("redirect:/community/detail/%s", id);

  }

}
