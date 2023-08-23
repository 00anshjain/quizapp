package com.example.quizapp.service;

import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.custom.exception.InvalidQuestionException;
import com.example.quizapp.model.Question;
import com.example.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionDao.findAll();
            if(questions.isEmpty()) {
                throw new InvalidQuizException("1003", "No question found while fetching all questions");
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }catch (Exception e) {
            throw new InvalidQuizException("1004", "Something went wrong in Question service layer while fetching all questions"  + e.getMessage());
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            List<Question> questions = questionDao.findByCategory(category);
            if(questions.isEmpty()) {
                throw new InvalidQuizException("1003", "No question found for category="+ category);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }catch (Exception e) {
            throw new InvalidQuizException("1004", "Something went wrong in Question service layer while fetching for category " + e.getMessage());
        }
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> addQuestion(Question question) {
        try {
            if (question.getQuestionTitle().isEmpty() || question.getOption1().isEmpty() ||
                    question.getOption2().isEmpty() || question.getRightAnswer().isEmpty())
                throw new InvalidQuestionException("1001", "Question don't have all required fields");
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
                throw new InvalidQuizException("1002", "Question cannot be added " + e.getMessage());
        }
        catch (Exception e) {
            throw new InvalidQuizException("1002", "Something went wrong in Question service layer while saving question" + e.getMessage());
        }
    }

}
