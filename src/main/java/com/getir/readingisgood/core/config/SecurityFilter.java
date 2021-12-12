package com.getir.readingisgood.core.config;

import com.getir.readingisgood.core.exception.BusinessException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityFilter extends OncePerRequestFilter
{

    public static final ThreadLocal<String> USER_EMAIL_TL = new ThreadLocal<>();
    public static final List<String> noFilterUrl = List.of("/api/v1/customers", "/api/v1/login", "/h2-console(.*)", "(.*)/webjars/(.*)",
            "(.*)/swagger(.*)/(.*)", "(.*)/v2/api-docs(.*)", "(.*)/error/(.*)", "(.*)/resources/(.*)", "(.*)/swagger(.*)");

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException
    {
        String jwt;
        try
        {
            String jwtHeader = req.getHeader("Authorization");
            if (jwtHeader == null || jwtHeader.isBlank())
            {
                throw new BusinessException("Invalid User");
            }
            jwt = jwtHeader.substring("Bearer ".length());
            if (!JwtTokenProvider.isValidToken(jwt))
            {
                throw new BusinessException("Invalid User");
            }
        }
        catch (BusinessException e)
        {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.getWriter().write("{\n\"message\": \"Invalid User\"\n}");
            return;
        }
        try
        {
            USER_EMAIL_TL.set(JwtTokenProvider.findEmail(jwt));
            filterChain.doFilter(req, resp);
        }
        finally
        {
            USER_EMAIL_TL.remove();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        String requestURI = request.getRequestURI();
        return noFilterUrl.stream().anyMatch(requestURI::matches);
    }
}
