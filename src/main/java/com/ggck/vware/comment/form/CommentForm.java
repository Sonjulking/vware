package com.ggck.vware.comment.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {

  @NotEmpty(message = "내용은 필수항목입니다.")
  @Size(max = 200, message = "200자이하로 작성해주세요.")
  private String content;

}
