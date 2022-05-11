package com.ilzya.springboot.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Dept {

  private long id;

  @NotEmpty(message = "部门名称不能为空")
  @NotNull(message = "部门名称不能为空")
  private String dname;

  @NotEmpty(message = "部门地址不能为空")
  @NotNull(message = "部门地址不能为空")
  private String address;

  private long status;


}
