package org.oop.sns.configuration.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oop.sns.model.User;
import org.oop.sns.service.UserService;
import org.oop.sns.util.JwtTokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get header
        final String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            log.error("Authorization header is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.substring(7);

            // check token is valid
            if (JwtTokenUtils.isExpired(token, key)) {
                log.error("Expired JWT token");
                filterChain.doFilter(request, response);
                return;
            }

            // get username from token
            String userName = JwtTokenUtils.getUserName(token, key);
            // check the user is valid
            User user = userService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))
            );
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
