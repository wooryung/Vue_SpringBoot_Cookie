package wrsung.backend.cookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class CookieProvider {
    public void createCookie(String email, HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", email);
        cookie.setMaxAge(60 * 30); // 30분
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
