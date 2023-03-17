package wrsung.backend.cookie;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CookieFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getRequestURI().equals("/api/login")) {
            chain.doFilter(request, response);
        } else {
            Cookie[] cookies = req.getCookies();
            boolean isValidCookie = false;

            // 쿠키 검증
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("myCookie")) {
                        isValidCookie = true;
                    }
                }
            }

            if (isValidCookie) {
                chain.doFilter(request, response);
            } else {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
