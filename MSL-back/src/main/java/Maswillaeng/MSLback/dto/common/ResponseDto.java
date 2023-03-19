package Maswillaeng.MSLback.dto.common;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {
    private String message;
    private T data;

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static ResponseDto<Object> of(String message) {
        return new ResponseDto<>(message);
    }

    public static <T> ResponseDto<T> of(String message, T data) {
        return new ResponseDto<>(message, data);
    }

}
