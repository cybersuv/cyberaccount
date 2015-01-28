package com.suv.flat.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.suv.flat.model.User;
import com.suv.flat.service.CoreService;



@Controller

public class LoginController {
	
	@Value("${application.name}") String appName;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	   public ModelAndView showLoginPage(ModelMap model) {
			model.addAttribute("orgList", CoreService.getOrganisationsMap());
			return new ModelAndView("login", "command", new User());
	   }
	@RequestMapping(value="/login", method = RequestMethod.POST)
	   public String processLogin(@ModelAttribute("SpringWeb")User login,HttpServletRequest request, ModelMap model) {
		if(CoreService.authenticateUser(login)){
			login=CoreService.getUserFromLogin(login);
		  model.addAttribute("username", login.getUser_name());
	      model.addAttribute("password", "Login success");
	      model.addAttribute("authuser", login);
	      model.addAttribute("appname", appName);
	      request.getSession().setAttribute("isadmin", CoreService.isAdmin(login));
	      request.getSession().setAttribute("authuser", login);
	      return "result";
		}else{
			login.setPassword("");
			model.addAttribute("command", login);
			model.addAttribute("orgList", CoreService.getOrganisationsMap());
			model.addAttribute("loginmessage", "Couldn't log you in ! Please check username/password/Organisation.");
		    return "login";
		}
	      
	   }
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	   public String processLogout(SessionStatus status,HttpSession session,ModelMap model) {
			status.setComplete();
			session.invalidate();
			return "redirect:/";
	   }
}