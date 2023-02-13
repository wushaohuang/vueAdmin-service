package com.markerhub.security;

import com.markerhub.utils.JwtUtils;
import com.mysql.cj.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    //完成security自动登录的过程
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isNullOrEmpty(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null) {
            throw new JwtException("Token 异常");
        }
        if (jwtUtils.isTokenExpired(claims)) {
            throw new JwtException("token已过期");
        }
        String username = claims.getSubject();
        //获取用户的权限信息
        //用户名，密码，权限信息；因为要实现自动登录所以设为null，因为目前还没有权限信息所以设为null
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, null);

        //设置认证主体
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
