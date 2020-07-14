package com.app.group15.SurveyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.List;

public interface ISurveyService {
    List<Question> getSurveyQuestionByInstructorID(int instructorID) throws SQLException, AwsSecretsManagerException;

    Survey getSurveyByCourseId(int courseId) throws SQLException, AwsSecretsManagerException;

    List<Question> getSurveyQuestionByCourseID(int courseID) throws SQLException, AwsSecretsManagerException;

    List<Question> getRemainingQuestionsForSurvey(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException;

    SurveyFormResponse getSurveyQuestionWithOptions(int courseId) throws SQLException, AwsSecretsManagerException;

    void createSurveyIfNotExists(int courseId) throws SQLException, AwsSecretsManagerException;

    int addQuestionToSurvey(int courseId, int questionId, String rule, int ruleValue) throws SQLException, AwsSecretsManagerException;

    void deleteSurveyQuestion(int questionId, int courseId) throws SQLException, AwsSecretsManagerException;

    void publishSurvey(int courseId) throws SQLException, AwsSecretsManagerException;

    void unPublishSurvey(int courseId) throws SQLException, AwsSecretsManagerException;

    void submitResponse(User user, SurveyFormResponse surveyFormResponse) throws SQLException, AwsSecretsManagerException;
}
