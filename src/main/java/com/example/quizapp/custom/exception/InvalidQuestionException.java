package com.example.quizapp.custom.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InvalidQuestionException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    @Getter
    private String errorCode;
    private String errorMessage;

    public InvalidQuestionException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public InvalidQuestionException() {

    }

}
