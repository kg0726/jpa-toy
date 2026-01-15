package jpa.practice.toy.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션이 있는지 먼저 확인
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            // 로그인이 안된 사용자라면 거절
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"로그인이 필요한 서비스입니다.\"}");
            return false;
        }
        return true;
    }
}
