package jpa.practice.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
/**
 * 사용자에게 보여줄 에러 응답에 대한 포맷을 지정함
 */
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
}
