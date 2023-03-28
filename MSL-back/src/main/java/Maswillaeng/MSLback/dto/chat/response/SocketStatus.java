package Maswillaeng.MSLback.dto.chat.response;

import Maswillaeng.MSLback.domain.enums.MessageEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;


@Getter
public class SocketStatus {
    private int status;

    private String message;

    private MessageEnum type;
    public SocketStatus(int status, String error,MessageEnum type) {
        this.status = status;
        this.message = error;
        this.type = type;
    }
}
