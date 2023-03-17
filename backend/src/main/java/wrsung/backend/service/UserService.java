package wrsung.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wrsung.backend.exception.NoSuchDataException;
import wrsung.backend.mapper.UserMapper;
import wrsung.backend.vo.UserVo;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserVo> getUserList() {
        List<UserVo> userList = userMapper.getUserList();
        if (userList.isEmpty())
            throw new NoSuchDataException("No such data exists.");
        return userList;
    }

    public UserVo getUserByEmail(String email) {
        UserVo userVo = userMapper.getUserByEmail(email);
        if (userVo == null)
            throw new NoSuchDataException("No such user exists.");
        return userVo;
    }
}
