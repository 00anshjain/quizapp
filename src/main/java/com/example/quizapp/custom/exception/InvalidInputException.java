package com.example.quizapp.custom.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Getter
@Setter
@Component
public class InvalidInputException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;

    public InvalidInputException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public InvalidInputException() {

    }
}
