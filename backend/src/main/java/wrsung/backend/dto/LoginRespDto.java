package wrsung.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import wrsung.backend.consts.ResCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRespDto {
    private Boolean authenticated;
    private String username;
    private int code = ResCode.SUCCESS.value();
    private String message;
}
