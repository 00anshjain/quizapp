package com.example.quizapp.controller;

import com.example.quizapp.custom.exception.ControllerException;
import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<?> getAllQuestions() {
            return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String category) {
            return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
            return questionService.addQuestion(question);
    }
}
