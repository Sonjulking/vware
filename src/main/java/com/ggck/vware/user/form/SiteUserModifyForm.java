package com.ggck.vware.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserModifyForm {

  @Size(min = 3, max = 25)
  @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
  private String userId;
  @Size(min = 3, max = 25)
  @NotEmpty(message = "사용자 닉네임은 필수 항목입니다.")
  private String userNickName;

  @NotEmpty(message = "이메일은 필수항목입니다.")
  @Email
  private String userEmail;

  @NotEmpty(message = "선호포지션 입력은 필수항목입니다.")
  private String preferredPosition;

}
