package com.project.commerce.global.auth;

import com.project.commerce.global.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();

        if (path.startsWith("/users/login")
                || path.equals("/users")
                || path.startsWith("/h2-console")) {

            filterChain.doFilter(request, response);
            return;
        }


        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("TOKEN MISSING");
            return;
        }

        String token = header.substring(7);

        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("INVALID TOKEN");
            return;
        }

        String email = JwtUtil.getEmail(token);

        // request에 사용자 정보 저장
        request.setAttribute("userEmail", email);


        // 통과
        filterChain.doFilter(request, response);
    }
}