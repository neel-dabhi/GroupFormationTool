package com.app.group15.courseManagement;


import com.app.group15.config.ServiceConfig;

import com.app.group15.userManagement.IAuthorizationService;
import com.app.group15.userManagement.ISessionService;

import com.app.group15.userManagement.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
public class GuestController {
	private IAuthorizationService authorizationService = ServiceConfig.getInstance().getAuthorizationService();
	private ISessionService sessionService = ServiceConfig.getInstance().getSessionService();
	private ICourseService courseService = ServiceConfig.getInstance().getCourseService();
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView guestHome(HttpServletRequest request) {
		authorizationService.setAllowedRoles(new String[]{"GUEST"});
		ModelAndView modelAndView;
		if (sessionService.isUserSignedIn(request)) {
			if (authorizationService.isAuthorized(request)) {
				User user = sessionService.getSessionUser(request);
				List<Course> courses = courseService.getCoursesList();
				modelAndView = new ModelAndView();
				modelAndView.setViewName("home");
				modelAndView.addObject("user", user);
				modelAndView.addObject("courses", courses);
				return modelAndView;
			} else {
//				System.out.println("----------------Unauthorized access for /user/home !!!----------------");
				modelAndView = new ModelAndView("redirect:/login");
			}
		} else {
//			System.out.println("----------------User not logged in !!!----------------");
			modelAndView = new ModelAndView("redirect:/login");
		}
		return modelAndView;
	}
}