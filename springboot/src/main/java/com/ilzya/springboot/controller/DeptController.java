package com.ilzya.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ilzya.springboot.entity.Dept;
import com.ilzya.springboot.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    DeptService deptService;

    /**
     * 查询所有部门信息
     * @return
     */
    @PostMapping(value = "/queryall")
    @ResponseBody
    public String queryAll(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",0);
        map.put("data",deptService.queryAll());
        return JSON.toJSONString(map);
    }

    /**
     * 删除部门
     * @param jsonParam
     * @return
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody JSONObject jsonParam){
        Map<String,Object> map = new HashMap<>();
        Long id = jsonParam.getLong("id");
        if(id == null || id < 0){
            map.put("status",-2);
            map.put("message","id错误");
        }else {
            boolean flag = deptService.delete(id);
            if(flag){
                map.put("status",0);
                map.put("message","删除成功");
            }else {
                map.put("status",-1);
                map.put("message","删除失败");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改部门状态
     * @param jsonParam
     * @return
     */
    @PostMapping(value = "/changeStatus")
    @ResponseBody
    public String changeStatus(@RequestBody JSONObject jsonParam){
        Map<String,Object> map = new HashMap<>();
        Long id = jsonParam.getLong("id");
        if(id == null || id < 0){
            map.put("status",-2);
            map.put("message","id错误");
        }else {
            boolean flag = deptService.changeStatus(id);
            if(flag){
                map.put("status",0);
                map.put("message","修改成功");
            }else {
                map.put("status",-1);
                map.put("message","修改失败");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改部门信息
     * @param dept
     * @return
     */
    @PostMapping(value = "/changeInfo")
    @ResponseBody
    public String changeInfo(@RequestBody @Valid Dept dept, BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            map.put("status",-1);
            map.put("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else{
            boolean flag = deptService.changeInfo(dept);
            if(flag){
                map.put("status",0);
                map.put("message","修改成功");
            }else {
                map.put("status",-1);
                map.put("message","修改失败");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @PostMapping(value = "/addInfo")
    @ResponseBody
    public String addInfo(@RequestBody @Valid Dept dept,BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            map.put("status",-1);
            map.put("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else {
            boolean flag = deptService.addInfo(dept);
            if(flag){
                map.put("status",0);
                map.put("message","添加成功");
            }else {
                map.put("status",-1);
                map.put("message","添加失败");
            }
        }
        return JSON.toJSONString(map);
    }


    /**
     * 分页
     * @param jsonParam
     * @return
     */
    @PostMapping(value = "/page")
    @ResponseBody
    public String page(@RequestBody JSONObject jsonParam){
        Dept dept = new Dept();
        dept.setAddress(jsonParam.getString("address"));
        dept.setDname(jsonParam.getString("dname"));
        Integer pageNum = jsonParam.getInteger("pageNum");
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,7);
        List<Dept> deptList = deptService.queryByNameAndAddress(dept);
        PageInfo<Dept> pageInfo = new PageInfo<Dept>(deptList);
        Map<String,Object> map = new HashMap<>();
        map.put("status",0);
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        map.put("pageSize",pageInfo.getPageSize());
        return JSON.toJSONString(map);
    }

}
