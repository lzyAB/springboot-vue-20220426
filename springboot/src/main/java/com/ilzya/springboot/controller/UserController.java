package com.ilzya.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.ilzya.springboot.entity.User;
import com.ilzya.springboot.service.UserService;
import com.ilzya.springboot.util.JwtUtil;
import com.ilzya.springboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
//跨域
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    /**
     * 用户登录，返回json数据
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody @Valid User user, BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            map.put("status",-1);
            map.put("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else {
            User u = userService.login(user.getUsername());
            //判断是否查询到数据,未查询到则没有该用户
            if(u != null){
                //判断密码是否相等
                if (u.getPassword().equals(user.getPassword().trim())){
                    //登录成功
                    map.put("status",0);
                    map.put("message","登录成功");
                    //生成一个随机id
                    String id = UUID.randomUUID().toString();
                    Map<String,Object> info = new HashMap<>();
                    info.put("username",u.getUsername());
                    info.put("id",u.getId());
                    info.put("uuid",id);
                    info.put("accesslevel",u.getAccesslevel());
                    info.put("headerimg",u.getHeaderimg());
                    info.put("email",u.getEmail());
                    String token = JwtUtil.sign(id, info);
                    map.put("token",token);
                    //存入Redis
                    RedisUtil.set(id,info);
                }else {
                    //密码错误
                    map.put("status",-3); //密码错误状态码
                    map.put("message","密码错误");
                }
            }else {
                //用户不存在
                map.put("status",-4); //用户不存在状态码
                map.put("message","该用户名不存在");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 根据token获取用户信息
     * @return
     */
    @PostMapping(value = "/userinfo")
    @ResponseBody
    public String userInfo(){
        Map<String,Object> map = new HashMap<>();
        String token = request.getHeader("token");
        Map<String, Object> info = JwtUtil.getInfo(token);
        map.put("status",0);
        map.put("data",info);
        return JSON.toJSONString(map);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping(value = "/logout")
    @ResponseBody
    public String loginOut(){
        Map<String,Object> map = new HashMap<>();
        String token = request.getHeader("token");
        Map<String, Object> info = JwtUtil.getInfo(token);
        String uuid = info.get("uuid").toString();
        if(RedisUtil.delete(uuid)){
            map.put("status",0);
            map.put("message","退出成功");
        }else {
            map.put("status",-1);
            map.put("message","退出失败");
        }
        return JSON.toJSONString(map);
    }


}
