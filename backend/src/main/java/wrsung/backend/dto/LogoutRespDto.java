package wrsung.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import wrsung.backend.consts.ResCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogoutRespDto {
    private Boolean loggedOut;
    private int code = ResCode.SUCCESS.value();
    private String message;
}
