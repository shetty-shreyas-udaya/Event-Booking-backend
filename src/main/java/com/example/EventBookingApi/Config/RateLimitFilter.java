package com.example.EventBookingApi.Config;

import com.example.EventBookingApi.Service.JwtService;
import com.example.EventBookingApi.Service.RateLimiterService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    private final JwtService jwtService;






    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String key = null;

        if(authHeader != null && authHeader.startsWith("Bearer"))
        {
            String token = authHeader.substring(7);

            if(jwtService.validateToken(token))
            {
                key = jwtService.extractUserName(token);
            }else {
                key = request.getRemoteAddr();
            }
        }else {
            key = request.getRemoteUser();
            if(key == null){
                key = request.getRemoteAddr();
            }
        }

        if(key == null) key = "UNKNOWN";
        Bucket bucket = rateLimiterService.resolveBucket(key);

        if(bucket.tryConsume(1))
        {
            filterChain.doFilter(request,response);
        }else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
        }
    }
}
