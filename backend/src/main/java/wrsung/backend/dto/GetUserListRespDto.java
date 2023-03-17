package wrsung.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import wrsung.backend.consts.ResCode;
import wrsung.backend.vo.UserVo;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserListRespDto {
    private List<UserVo> userList;
    private String username;
    private int code = ResCode.SUCCESS.value();
    private String message;
}
