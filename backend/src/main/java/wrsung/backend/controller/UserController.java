package wrsung.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wrsung.backend.consts.ResCode;
import wrsung.backend.cookie.CookieProvider;
import wrsung.backend.dto.*;
import wrsung.backend.exception.NoSuchDataException;
import wrsung.backend.service.UserService;
import wrsung.backend.vo.UserVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CookieProvider cookieProvider;

    @GetMapping("/api/")
    public GetUserListRespDto home(HttpServletRequest request) {
        GetUserListRespDto getUserListRespDto = new GetUserListRespDto();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String email = cookie.getValue();
            List<UserVo> userList = userService.getUserList();
            String username = userService.getUserByEmail(email).getUsername();

            getUserListRespDto.setUserList(userList);
            getUserListRespDto.setUsername(username);
        }
        return getUserListRespDto;
    }

    @PostMapping("/api/login")
    public LoginRespDto login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse response) {
        LoginRespDto loginRespDto = new LoginRespDto();
        try {
            String email = loginReqDto.getEmail();
            String password = loginReqDto.getPassword();
            UserVo userVo = userService.getUserByEmail(email);

            if (password.equals(userVo.getPassword())) {
                loginRespDto.setAuthenticated(true);
                loginRespDto.setUsername(userVo.getUsername());
                cookieProvider.createCookie(email, response);
            } else {
                loginRespDto.setAuthenticated(false);
                loginRespDto.setCode(ResCode.NO_SUCH_DATA.value());
                loginRespDto.setMessage("비밀번호가 일치하지 않습니다.");
            }
        } catch (NoSuchDataException e) {
            loginRespDto.setAuthenticated(false);
            loginRespDto.setCode(ResCode.NO_SUCH_DATA.value());
            loginRespDto.setMessage("존재하지 않는 이메일입니다.");
        } catch (Exception e) {
            log.error("[UserController authenticate()]", e);
            loginRespDto.setCode(ResCode.UNKNOWN.value());
            loginRespDto.setMessage(e.getLocalizedMessage());
        }
        return loginRespDto;
    }

    @PostMapping("/api/logout")
    public LogoutRespDto logout(HttpServletRequest request, HttpServletResponse response) {
        LogoutRespDto logoutRespDto = new LogoutRespDto();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookieProvider.deleteCookie(cookie, response);
            logoutRespDto.setLoggedOut(true);
        }
        return logoutRespDto;
    }

    @GetMapping("/api/relogin")
    public ReloginRespDto relogin(HttpServletRequest request, HttpServletResponse response) {
        ReloginRespDto reloginRespDto = new ReloginRespDto();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String email = cookie.getValue();
            String username = userService.getUserByEmail(email).getUsername();
            reloginRespDto.setUsername(username);
        }
        return reloginRespDto;
    }
}
