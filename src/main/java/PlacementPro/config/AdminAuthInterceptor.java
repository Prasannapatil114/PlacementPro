
package PlacementPro.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        Object admin = request.getSession()
                .getAttribute("loggedInAdmin");

        if (Boolean.TRUE.equals(admin)) {
            return true;
        }

        response.sendRedirect("/admin/login");
        return false;
    }
}
