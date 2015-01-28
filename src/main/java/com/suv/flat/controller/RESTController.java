package com.suv.flat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suv.flat.model.User;
import com.suv.flat.service.CoreService;

@Controller
@RequestMapping("api")
public class RESTController {
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	   public User processLogin(@RequestBody User login,HttpServletRequest request, ModelMap model) {
		System.out.println("Parameter : " + login.toString());
		if(CoreService.authenticateUser(login)){
			login=CoreService.getUserFromLogin(login);
		}else{
			login.setUserid(-1);
		}
		return login;
	   }

}
