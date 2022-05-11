package com.ilzya.springboot.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class User {

  private long id;

  @NotNull(message = "用户名不能为空")
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @NotNull(message = "密码不能为空")
  @NotEmpty(message = "密码不能为空")
  private String password;
  private Long accesslevel;
  private String headerimg;
  private String email;

}
