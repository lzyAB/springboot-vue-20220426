package com.ilzya.springboot.interceptor;

import com.alibaba.fastjson.JSON;
import com.ilzya.springboot.util.JwtUtil;
import com.ilzya.springboot.util.RedisUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Map<String,Object> map = new HashMap<>();
        //从 http 请求头中取出 token
        String token = request.getHeader("token");
        //System.out.println("此处测试是否拿到了token：" + token);

        if (token == null) {
            map.put("status",401);
            map.put("message","请登录");
            returnJson(response, JSON.toJSONString(map));
            return false;
        }

        //验证 token
        if(!JwtUtil.checkSign(token)){
            map.put("status",401);
            map.put("message","状态过期,请重新登录");
            returnJson(response,JSON.toJSONString(map));
            return false;
        }

        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getId(token);

        //获取 token 中的其他数据
        Map<String, Object> info = JwtUtil.getInfo(token);
        String uuid = info.get("uuid").toString();
//        System.out.println("info.get(\"uuid\").toString()==============="+uuid);
//        System.out.println("RedisUtil.get(uuid)=============="+RedisUtil.get(uuid));
        if("".equals(RedisUtil.get(uuid))){
            map.put("status",401);
            map.put("message","状态过期,请重新登录");
            returnJson(response,JSON.toJSONString(map));
            return false;
        }

        /**
         * 这里直接把其他权限用户排除了,我还没买接触过其他权限管理框架,下个demo会做完善
         */
        Long accesslevel = Long.parseLong(info.get("accesslevel").toString());
        if(accesslevel != 1){
            map.put("status",401);
            map.put("message","权限不足");
            returnJson(response,JSON.toJSONString(map));
            return false;
        }

        //info.forEach((k, v) -> System.out.println(k + ":" + v));
        return true;
    }


    /**
     * 返回json，前后端分离
     * @param response
     * @param result
     */
    private void returnJson(HttpServletResponse response , String result) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(result);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null) {
                writer.close();
            }
        }
    }

}
