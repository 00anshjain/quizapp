package com.example.quizapp.service;

import com.example.quizapp.custom.exception.InvalidQuizException;
import com.example.quizapp.custom.exception.QuizSubmissionException;
import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            if (numQ == 0) {
                throw new InvalidQuizException("1007", "Quiz cannot have no questions");
            }
            if (title.isEmpty()) {
                throw new InvalidQuizException("1008", "No title found for quiz creation");
            }
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            System.out.println(quiz);
            quizDao.save(quiz);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidQuizException("1009", "IllegalArgumentException while creating quiz " + e.getMessage());
        }
        catch (Exception e) {
            throw new InvalidQuizException("1010", "Exception while creating quiz " + e.getMessage());
        }

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        try {
            Optional<Quiz> quiz = quizDao.findById(id);
            // Optional because data might come, might not come, if the quizId is present then only data will come
            // So to avoid getting NULL Value Exception we use Optional here
            if (quiz.isEmpty()) {
                throw new InvalidQuizException("1011", "No quiz found for quiz_id = " + id);
            }
            List<Question> questionsFromDB = quiz.get().getQuestions();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
        }
        catch (Exception e) {
            throw new InvalidQuizException("1011", "Exception fetching quiz for quiz_id = " + id);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> quiz = Optional.of(quizDao.findById(id).get());
            List<Question> questions = quiz.get().getQuestions();
            int right = 0;
            int i = 0;
            for (Response response : responses) {
                String resp = response.getResponse();
                if(resp.isEmpty()) {
                    throw new QuizSubmissionException("1013", "No response submitted for a question");
                }

                if (resp.equals(questions.get(i).getRightAnswer()))
                    right++;
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        }
        catch(Exception e) {
            throw new QuizSubmissionException("1012", "Error while submitting quiz " + e.getMessage());
        }
    }
}
