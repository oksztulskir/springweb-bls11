package com.sda.filter;

import com.sda.exceptions.NotFoundException;
import com.sda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
@Slf4j
@Component
public class AuthorizationFilter implements Filter {
    private final UserService userService;

    public AuthorizationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Processing request filter: {}", request);
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            final String email = ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttribute("email");
            try {
                userService.findByEmail(email);
            } catch (NotFoundException ex) {
                ((HttpServletResponse) response).sendRedirect("/logout");

                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initializing auth filter ...");
    }
}
