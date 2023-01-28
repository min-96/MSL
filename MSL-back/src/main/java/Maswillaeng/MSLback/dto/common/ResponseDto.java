package Maswillaeng.MSLback.dto.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
public class ResponseDto<T> {

    private int status;
    private T data;

    public ResponseDto(int status) {
        this.status = status;
    }

    public static ResponseDto<?> of(HttpStatus httpStatus) {
        return new ResponseDto<>(httpStatus.value());
    }

    public static <T> ResponseDto<T> of(HttpStatus httpStatus, T data) {
        return new ResponseDto<>(httpStatus.value(), data);
    }
}
