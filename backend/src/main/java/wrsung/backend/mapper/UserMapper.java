package wrsung.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import wrsung.backend.vo.UserVo;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getUserList();
    UserVo getUserByEmail(String email);
}
