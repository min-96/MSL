package Maswillaeng.MSLback.dto.chat.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;


@Getter
public class SocketStatus {
    private int status;

    private String message;
    public SocketStatus(int status, String error) {
        this.status = status;
        this.message = error;
    }
}
