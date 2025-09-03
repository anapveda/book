package com.example.Booking.JwtAuthenticationFilter;

import com.example.Booking.Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        System.out.println(jwt);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = jwtUtil.extractRole(jwt);
        System.out.println(role);
        String email = claims.getSubject();
        System.out.println(email);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, null, authorities);
        System.out.println(authToken);
        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);

    }
}
/*
* Aspect	@Component	@Configuration
Purpose	Marks a simple Spring bean (auto-discovered)	Defines a class that creates beans
Bean Creation	Class itself is the bean	Contains @Bean methods that return beans
Usage	Services, Controllers, Repositories, Utilities	App-wide configs (Kafka, DB, Security, CORS, etc.)
Detection	Auto-scanned in @ComponentScan	Auto-scanned (because @Configuration is also a @Component)
* */