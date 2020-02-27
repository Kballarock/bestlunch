package by.bestlunch.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("logoutSuccessHandler")
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        final HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }

        if (authentication != null) {
            log.debug("User: " + "[" + authentication.getName() + "]"
                    + " logout successfully and redirect to 'Login' page.");
        }
        response.sendRedirect("login?logout=true");
    }
}
