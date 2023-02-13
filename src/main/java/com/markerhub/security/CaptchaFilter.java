package com.markerhub.security;

import com.markerhub.common.exception.CaptchaException;
import com.markerhub.common.lang.Const;
import com.markerhub.utils.RedisUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFaliureHandler loginFaliureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();

        if ("/login".equalsIgnoreCase(url) && httpServletRequest.getMethod().equals("POST")) {
            //校验验证码

            try {
                validate(httpServletRequest);
            } catch (CaptchaException e) {
                //交给认证失败处理器
                loginFaliureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            //如果不正确，则跳转到认证失败处理器

        }

    }

    //校验验证码逻辑
    private void validate(HttpServletRequest httpServletRequest) {

        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("token");

        if (StringUtils.isNullOrEmpty(code) || StringUtils.isNullOrEmpty(key)) {
            throw new CaptchaException("验证码错误");
        }
        if (code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
            throw new CaptchaException("验证码错误");
        }

        //验证码是一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY,key);

    }
}
