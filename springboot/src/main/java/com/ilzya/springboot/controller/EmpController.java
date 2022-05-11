package com.ilzya.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ilzya.springboot.entity.Emp;
import com.ilzya.springboot.service.EmpService;
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
@RequestMapping(value = "/emp")
public class EmpController {

    @Autowired
    EmpService empService;

    /**
     * 查询员工信息及部门信息
     * @return
     */
    @PostMapping(value = "/queryed")
    @ResponseBody
    public String queryEmpAndDept(){
        String json = JSON.toJSONString(empService.queryEmpAndDept());
        return json;
    }

    /**
     * 删除员工信息
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
            boolean flag = empService.delete(id);
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
     * 根据id查询员工信息
     * @param jsonParam
     * @return
     */
    @PostMapping(value = "/queryid")
    @ResponseBody
    public String queryByid(@RequestBody JSONObject jsonParam){
        Long id = jsonParam.getLong("id");
        Map<String,Object> map = new HashMap<>();
        Emp emp = empService.queryById(id);
        map.put("status",0);
        map.put("data",emp);
        return JSON.toJSONString(map);
    }

    /**
     * 修改员工信息
     * @param emp
     * @return
     */
    @PostMapping(value = "/changeInfo")
    @ResponseBody
    public String changeInfo(@RequestBody Emp emp) {
        Map<String,Object> map = new HashMap<>();
        boolean flag = empService.changeInfo(emp);
        if(flag){
            map.put("status",0);
            map.put("message","修改成功");
        }else {
            map.put("status",-1);
            map.put("message","修改失败");
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
        Integer pageNum = jsonParam.getInteger("pageNum");
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,7);
        List<Emp> empList = empService.queryByNameAndDeptId(jsonParam.getString("ename"),jsonParam.getLong("deptno"));
        PageInfo<Emp> pageInfo = new PageInfo<Emp>(empList);
        Map<String,Object> map = new HashMap<>();
        map.put("status",0);
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        map.put("pageSize",pageInfo.getPageSize());
        return JSON.toJSONString(map);
    }

    /**
     * 添加用户
     * @param emp
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/addInfo")
    @ResponseBody
    public String addInfo(@RequestBody @Valid Emp emp, BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            map.put("status",-1);
            map.put("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else {
            boolean flag = empService.addInfo(emp);
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
}
