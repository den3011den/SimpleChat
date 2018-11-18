package bds.model;

import bds.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// действия, которые будем делать при удачной аутентификации пользователя
@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String login = authentication.getName();
        MyAuthenticationSuccessHandler.LOG.info("login user: " + login);
        // запоминаем максимальный текущий id сообщения из таблицы сообщений БД messages
        // При следующем запросе клиентом новых сообщений все сообщения в messages, у которых messages.id большие этого id,
        // будут считаться новыми и их сервер будет передавать клиенту
        userService.rememberTopMessageIdForUser(authentication.getName());

        setDefaultTargetUrl("/chat");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}