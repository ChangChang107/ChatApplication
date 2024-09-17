package com.calmero.filter;

import com.calmero.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {
    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";
    private static final String ROLES_CLAIM = "authorities";
    private static final String SCOPED_ROLES_CLAIM = "scoped-authorities";
    private static final String AUTHORIZATION = "Authorization";
    private final AuthUtil authUtil;

    @SneakyThrows
    public static void getExceptionResponse(final HttpServletResponse response, final HttpStatus httpStatus) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        var map = new HashMap<String, Object>();
        map.put("code", httpStatus);
        map.put("status", HttpStatus.FORBIDDEN);
        response.getWriter().write(map.toString());
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response, final FilterChain filterChain) {
        try {
            final Authentication authentication = extractAuthentication(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            getExceptionResponse(response, HttpStatus.UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(httpServletRequest, response);
    }

    private Authentication extractAuthentication(final HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        return authUtil.generateAuthentication(token);
    }
}