package com.ggck.vware;

import com.ggck.vware.community_post.service.CommunityPostSerivce;
import com.ggck.vware.user.entity.SiteUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VwareApplicationTests {

  @Autowired
  private CommunityPostSerivce communityPostSerivce;

  @Test
  void contextLoads() {
    for (int i = 1; i <= 300; i++) {
      String subject = String.format("테스트 데이터입니다:[%03d]", i);
      String content = "내용무";
      String postType = "자유";
      SiteUserEntity siteUser = new SiteUserEntity();
      siteUser.setUserNumber(193L);
      this.communityPostSerivce.create(subject, content, siteUser, postType);
    }

  }
}