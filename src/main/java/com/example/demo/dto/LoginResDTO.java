package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResDTO {
    private boolean isValid;
    private String token;
    private String role;
    private String username;
    private String errorMessage;
    private Integer errorCode;


    public static LoginResDTO createErrorResponse(Error error){
        return new LoginResDTO(false, null, null, null, error.getMessage(), error.getCode());
    }

    public static LoginResDTO createSuccessResponse(String token, String role, String username){
        return new LoginResDTO(true, token, role, username, null, null);
    }


    public enum Error{
        USERNAME_NOT_FOUND(1,"Username không tồn tại"),
        WRONG_PASSWORD(2,"Sai mật khẩu, vui lòng kiểm tra lại");

        private final int code;
        private final String message;

        Error(int code, String message){
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
