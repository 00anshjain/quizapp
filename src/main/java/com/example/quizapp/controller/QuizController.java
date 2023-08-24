package com.example.quizapp.controller;

import com.example.quizapp.custom.exception.ControllerException;
import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.custom.exception.QuizSubmissionException;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Response;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @RequestMapping("create")
    public ResponseEntity<?> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
            return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getQuizQuestions(@PathVariable Integer id) {
            return quizService.getQuizQuestions(id);
    }
    @PostMapping("submit/{id}")
    public ResponseEntity<?> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
            return quizService.calculateResult(id, responses);
    }
}
