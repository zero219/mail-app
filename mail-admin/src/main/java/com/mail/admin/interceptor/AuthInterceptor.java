package com.mail.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.mail.admin.entity.SysUser;
import com.mail.admin.util.RedisKeyUtils;
import com.mail.common.core.Result;
import com.mail.common.enums.ResultCodeEnum;
import com.mail.admin.util.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


/**
 * 登录验证拦截器
 */
@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     *  controller之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求头token
        String token = request.getHeader("token");

        // 如果token为空，返回错误提示
        if (StringUtils.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 如果token不为空，拿着token查询redis
        String userInfoString = redisTemplate.opsForValue().get("user:login" + token);

        // 如果redis查询不到数据，返回错误提示
        if (StringUtils.isEmpty(userInfoString)) {
            responseNoLoginInfo(response);
            return false;
        }

        // 把用户信息放到ThreadLocal里面
        SysUser sysUser = JSON.parseObject(userInfoString, SysUser.class);
        ThreadLocalUtils.setSysUserInfo(sysUser);

        // 把redis用户信息数据更新过期时间
        redisTemplate.expire(RedisKeyUtils.USER_LOGIN_KEY + token, 30, TimeUnit.MINUTES);

        // 放行
        return true;
    }

    /**
     * Controller 方法执行之后，视图渲染之前 被调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     *  整个请求完成之后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        ThreadLocalUtils.removeSysUserInfo();
    }

    /**
     * 未登录提示
     *
     * @param response
     * @throws IOException
     */
    private void responseNoLoginInfo(HttpServletResponse response) throws IOException {
        Result<Object> result = new Result(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getMessage(), null, false);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            String jsonString = JSON.toJSONString(result);
            writer.print(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
