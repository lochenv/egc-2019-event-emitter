package be.vlproject.egcevent.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtHelper jwtHelper;

    @Autowired
    public JwtAuthorizationFilter(final JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith(SecurityConstants.JWT_BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String bearer = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (bearer.startsWith(SecurityConstants.JWT_BEARER_PREFIX)) {
            String token = StringUtils.remove(bearer, SecurityConstants.JWT_BEARER_PREFIX);

            if (jwtHelper.isValid(token)) {
                // parse the token.
                try {
                    User user = jwtHelper.extractUser(token);

                    if (user != null) {
                        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    }
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
}
