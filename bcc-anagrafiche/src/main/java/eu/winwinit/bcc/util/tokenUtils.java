package eu.winwinit.bcc.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import eu.winwinit.bcc.security.JwtAuthenticationFilter;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;

public class tokenUtils {
	
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public String getUsernameFromRequest(HttpServletRequest request) {
    	String userName = null;
        try {
            String jwt = getJWTFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {

                userName = jwtTokenProvider.getUsernameFromJWT(jwt);
                //Set<GrantedAuthority> roles = jwtTokenProvider.getRolesFromJWT(jwt);
                
            }
        } catch (Exception e) {
            log.error("Could not retrieve username from request", e);
            throw e;
        }
        return userName;
    }

    public Set<GrantedAuthority> getRolesFromRequest(HttpServletRequest request) {
    	Set<GrantedAuthority>  roles = null;
        try {
            String jwt = getJWTFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                roles = jwtTokenProvider.getRolesFromJWT(jwt);
            }
        } catch (Exception e) {
            log.error("Could not retrieve roles from request", e);
            throw e;
        }
        return roles;
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length());
        } else if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }    

}
