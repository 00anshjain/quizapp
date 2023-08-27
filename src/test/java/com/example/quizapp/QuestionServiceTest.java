package com.example.quizapp;

import com.example.quizapp.custom.exception.InvalidQuestionException;
import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionService questionService;
    @Test
    void testGetAllQuestionsNoQuestionsFound() {
        when(questionDao.findAll()).thenReturn(new ArrayList<>());
//        assertEquals("1003", questionService.getAllQuestions().getErrorCode());
        InvalidQuizException ex = assertThrows(
                InvalidQuizException.class,
                () -> questionService.getAllQuestions());
        assertEquals("1003", ex.getErrorCode());
    }

    @Test
    void getQuestionsByCategoryNoQuestionFound() {
        String category = "testing";
        when(questionDao.findByCategory(category)).thenReturn(new ArrayList<>());
//        assertEquals("1003", questionService.getAllQuestions().getErrorCode());
        InvalidQuizException ex = assertThrows(
                InvalidQuizException.class,
                () -> questionService.getQuestionsByCategory(category));
        assertEquals("1003", ex.getErrorCode());
    }

    @Test
    void addQuestionMissingFields() {
        Question question = new Question();
        InvalidQuestionException ex = assertThrows(
                InvalidQuestionException.class,
                () -> questionService.addQuestion(question));
        assertEquals("1001", ex.getErrorCode());
    }

    @Test
    void addQuestionSaveError() {
        Question question = new Question();
        question.setQuestionTitle("testing question");
        question.setOption1("tempA");
        question.setOption2("tempB");
        question.setRightAnswer("tempA");
        question.setCategory("testing");
        doThrow(new IllegalArgumentException("Invalid argument")).when(questionDao).save(question);
        InvalidQuizException ex = assertThrows(
                InvalidQuizException.class,
                () -> questionService.addQuestion(question));
        assertEquals("1002", ex.getErrorCode());
    }

    @Test
    void testGetAllQuestionsSuccess() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        when(questionDao.findAll()).thenReturn(questions);
        assertEquals(HttpStatus.OK, questionService.getAllQuestions().getStatusCode());
    }
}
