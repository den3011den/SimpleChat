package bds.model;

import bds.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyLogoutSuccessHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            String login = authentication.getName();
            MyLogoutSuccessHandler.LOG.info("logout user: " + login);
            userService.forgetTopMessageIdForUser(login);
        }

        setDefaultTargetUrl("/home");
        super.onLogoutSuccess(request, response, authentication);
    }
}