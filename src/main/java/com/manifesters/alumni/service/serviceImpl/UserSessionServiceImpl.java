package com.manifesters.alumni.service.serviceImpl;

import com.manifesters.alumni.service.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSessionServiceImpl implements UserSessionService {

    @Override
    public String getUserSessionId() {
        String sessionId = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && !auth.getPrincipal().equals("anonymousUser")) {
            sessionId = ((WebAuthenticationDetails)auth.getDetails()).getSessionId();
        }
        return sessionId;
    }
}
