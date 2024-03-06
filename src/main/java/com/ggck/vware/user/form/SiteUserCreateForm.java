package com.ggck.vware.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
//@Builder
public class SiteUserCreateForm {

  @Size(min = 3, max = 25)
  @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
  //@Pattern(regexp = "^[a-zA-Z0-0]*$", message = "사용자 ID는 영어랑 숫자만 가능합니다.")
  @Pattern(regexp = "^[a-z0-9]*$", message = "사용자 ID는 영어 소문자와 숫자만 가능합니다.")
  private String userId;
  @Size(min = 3, max = 25)
  @NotEmpty(message = "사용자 닉네임은 필수 항목입니다.")
  private String userNickName;

  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8.20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8 ~20자의 비밀번호여야 합니다.")
  private String password1;

  @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
  private String password2;

  @NotEmpty(message = "이메일은 필수항목입니다.")
  @Email
  private String userEmail;

  @NotEmpty(message = "선호포지션 입력은 필수항목입니다.")
  private String preferredPosition;

}
