package com.example.quizapp.advice;

import com.example.quizapp.custom.exception.InvalidQuestionException;
import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.custom.exception.QuizSubmissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<String> handleInvalidQuestion(InvalidQuestionException invalidQuestionException){
        return new ResponseEntity<String>("Got Invalid Question Exception, Kindly check",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuizException.class)
    public ResponseEntity<String> handleInvalidQuiz(InvalidQuizException invalidQuizException){
        return new ResponseEntity<String>("Invalid Request for quiz creation, Kindly check",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizSubmissionException.class)
    public ResponseEntity<String> handleInvalidQuizSubmission(QuizSubmissionException quizSubmissionException){
        return new ResponseEntity<String>("Check your responses, Invalid quiz submission request",HttpStatus.BAD_REQUEST);
    }


}
