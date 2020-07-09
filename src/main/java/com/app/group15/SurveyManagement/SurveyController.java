package com.app.group15.SurveyManagement;


import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.IQuestionManagerService;
import com.app.group15.QuestionManager.Question;
import com.app.group15.UserManagement.SessionManagement.IAuthorizationService;
import com.app.group15.UserManagement.SessionManagement.ISessionService;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Controller
public class SurveyController {

    private IAuthorizationService authorizationService = ServiceConfig.getInstance().getAuthorizationService();
    private CourseAbstractDao courseDao = AppConfig.getInstance().getCourseDao();
    private ISessionService sessionService = ServiceConfig.getInstance().getSessionService();
    private ISurveyService surveyService = ServiceConfig.getInstance().getSurveyService();
    private IQuestionManagerService questionManagerService=ServiceConfig.getInstance().getQuestionManagerService();

    @RequestMapping(value = "/instructor/survey", method = RequestMethod.GET)
    public ModelAndView manageSurveyGET(HttpServletRequest request, @RequestParam String courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR"});
        String sortColumn="questionId";
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
					ArrayList<Question> allQuestionsOfInstructor = (ArrayList<Question>) questionManagerService.getAllQuestionsOfInstructor(user.getId(), sortColumn);
                    List<Question> questionList = new ArrayList<>();
                    Course courseEntity = (Course) courseDao.get(Integer.parseInt(courseId));
                    try {
                        questionList = surveyService.getSurveyQuestionByCourseID(courseEntity.getId());
                    } catch (Exception e) {
                        GroupFormationToolLogger.log(Level.INFO, "Exception while getting Questions", e);
                    }

                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("instructor/survey");
                    modelAndView.addObject("allQuestionsOfInstructor",allQuestionsOfInstructor);
                    modelAndView.addObject("courseId", courseId);
                    modelAndView.addObject("userEntity", user);
                    modelAndView.addObject("courseEntity", courseEntity);
                    modelAndView.addObject("questionList", questionList);


                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

}
