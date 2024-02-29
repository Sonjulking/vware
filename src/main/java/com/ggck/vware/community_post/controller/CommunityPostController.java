package com.ggck.vware.community_post.controller;

import com.ggck.vware.comment.repository.CommentRepository;
import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.community_post.repository.CommunityPostRepository;
import com.ggck.vware.community_post.service.CommunityPostSerivce;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor //final이나 @Nonul인 필드 값만 파라미터로 받는 생성자를 만듬.
@RequestMapping("/community")
public class CommunityPostController {

  private final CommunityPostRepository communityPostRepository;
  private final CommunityPostSerivce communityPostSerivce;

  @GetMapping()
  public String communityMain(Model model) {

    List<CommunityPostEntity> communityPostList = this.communityPostRepository.findAll();
    model.addAttribute("postList", communityPostList);
    return "community/community";
  }

  @GetMapping(value = "/detail/{id}")
  public String postDetail(Model model, @PathVariable("id") Integer id) {

    CommunityPostEntity communityPostEntity = this.communityPostSerivce.getPost(id);
    model.addAttribute("postDetail", communityPostEntity);

    return "community/post_view";

  }


}