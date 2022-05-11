package com.ilzya.springboot.entity;

import lombok.Data;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Emp {

  private long id;

  @NotEmpty(message = "员工姓名不能为空")
  @NotNull(message = "员工姓名不能为空")
  private String ename;

  @NotEmpty(message = "员工手机号不能为空")
  @NotNull(message = "员工手机号不能为空")
  private String phone;

  @NotEmpty(message = "员工职位不能为空")
  @NotNull(message = "员工职位不能为空")
  private String job;

  private java.sql.Date birdate;

  @NotNull(message = "员工工资不能为空")
  private double sal;

  @NotEmpty(message = "员工身份证不能为空")
  @NotNull(message = "员工身份证不能为空")
  private String carno;

  @NotNull(message = "员工部门不能为空")
  @Min(value = 1,message = "请填写正确的员工部门")
  private long deptno;

  private Dept dept;


}
