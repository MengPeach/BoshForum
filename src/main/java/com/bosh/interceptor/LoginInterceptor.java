package com.bosh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bosh.entity.User;
import com.bosh.service.UserService;
import com.bosh.utils.CookieUtils;

/**
 * 
 * @author wangmt
 * @date 2018年9月20日
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
    private UserService userService;

    @Value("${cookie_name}")
    private String CookieName;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String token = CookieUtils.getCookieValue(httpServletRequest, "QUARK_TOKEN");
        if (token==null) {
            // 跳转到登录界面
            httpServletResponse.sendRedirect("/user/login");
            // 返回false
            return false;
        }
        User user = userService.getUserByApi(token);

        // 取不到用户信息
        if (user == null) {
            // 跳转到登录界面
            httpServletResponse.sendRedirect("/user/login");
            // 返回false
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
