package com.suv.flat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suv.flat.model.Owners;
import com.suv.flat.model.Reasons;
import com.suv.flat.model.Transactions;
import com.suv.flat.model.User;
import com.suv.flat.service.CoreService;

@Controller
public class TransactionController {
	@RequestMapping(value="/depositentry", method = RequestMethod.GET)
	   public ModelAndView showDepositPage(ModelMap model,HttpServletRequest request) {
			model.addAttribute("reasonList", CoreService.getReasonMap(((User)request.getSession().getAttribute("authuser")).getOrg_id()));
			return new ModelAndView("deposite", "command", new Transactions());
	   }
	
	@RequestMapping(value="/submitdeposit", method = RequestMethod.POST)
	public @ResponseBody
	String submitDeposit(@ModelAttribute("SpringWeb")Transactions trtx,HttpSession session,ModelMap model) {
		System.out.println("Session : " + (User)session.getAttribute("authuser"));	
			trtx.setTrtx_user(((User)session.getAttribute("authuser")).getUserid());
			String trtx_id=CoreService.processDeposit(trtx,((User)session.getAttribute("authuser")).getOrg_id());
			return trtx_id;
	   }
	
	@RequestMapping(value="/expenseentry", method = RequestMethod.GET)
	   public ModelAndView showExpensePage(ModelMap model,HttpServletRequest request) {
			model.addAttribute("reasonList", CoreService.getReasonMap(((User)request.getSession().getAttribute("authuser")).getOrg_id()));
			return new ModelAndView("expense", "command", new Transactions());
	   }
	
	@RequestMapping(value="/submitexpense", method = RequestMethod.POST)
	public @ResponseBody
	String submitExpense(@ModelAttribute("SpringWeb")Transactions trtx,HttpSession session,ModelMap model) {
		System.out.println("Session : " + (User)session.getAttribute("authuser"));	
			trtx.setTrtx_user(((User)session.getAttribute("authuser")).getUserid());
			String trtx_id=CoreService.processExpense(trtx,((User)session.getAttribute("authuser")).getOrg_id());
			return trtx_id;
	   }
	
	@RequestMapping(value="/reportindex", method = RequestMethod.GET)
	   public String showReportIndex(ModelMap model) {
			return "reportIndex";
	   }
	
	@RequestMapping(value = "/getAllOwners", method = RequestMethod.GET)
	public String getOwners(HttpServletRequest request,ModelMap model) {
 		if(CoreService.isSessonActive(request)){
		model.addAttribute("responseTable", CoreService.getOwnersHtml(request));
		model.addAttribute("sessionStatus", "OK");
 		}else{
 			model.addAttribute("sessionStatus", "NOT_OK"); 
 		}
 		return "ownersReport";
 	}
	
	@RequestMapping(value = "/getOwners", method = RequestMethod.GET)
	public @ResponseBody
	List<Owners> getTags(@RequestParam String term) {
		return CoreService.getOwnersFromSearch(term);
 	}
	
	/** Owner creation/modification **/
	
	@RequestMapping(value="/showcreateowner", method = RequestMethod.GET)
	   public ModelAndView showOwnerUI(ModelMap model) {
			model.addAttribute("floorList", CoreService.getFloors());
			return new ModelAndView("ownerUI", "command", new Owners());
	   }
	
	@RequestMapping(value="/fetchowner", method = RequestMethod.GET)
	   public ModelAndView fetchOwner(@RequestParam int owner_id,ModelMap model) {
			model.addAttribute("floorList", CoreService.getFloors());
			return new ModelAndView("ownerUI", "command", CoreService.getOwnerFromID(owner_id));
	   }
	
	@RequestMapping(value="/saveowner", method = RequestMethod.POST)
	public @ResponseBody
	String submitOwner(@ModelAttribute("SpringWeb")Owners owners,HttpSession session,ModelMap model) {
		System.out.println("Session : " + (User)session.getAttribute("authuser"));	
			String trtx_id=CoreService.saveOwner(owners,((User)session.getAttribute("authuser")).getOrg_id());
			return trtx_id;
	   }
	
	/** User creation/modification **/
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public String getUsers(HttpServletRequest request,ModelMap model) {
 		if(CoreService.isSessonActive(request)){
		model.addAttribute("responseTable", CoreService.getUsersHtml(request));
		model.addAttribute("sessionStatus", "OK");
 		}else{
 			model.addAttribute("sessionStatus", "NOT_OK"); 
 		}
 		return "ownersReport";
 	}
	
	@RequestMapping(value="/showcreateuser", method = RequestMethod.GET)
	   public ModelAndView showUserUI(ModelMap model,HttpServletRequest request) {
			model.addAttribute("roleList", CoreService.getRolesMap());
			model.addAttribute("orgList", CoreService.getOrganisationsMap());
			User user=new User();
			user.setOrg_id(((User)request.getSession().getAttribute("authuser")).getOrg_id());
			return new ModelAndView("userUI", "command", user);
	   }
	
	@RequestMapping(value="/fetchuser", method = RequestMethod.GET)
	   public ModelAndView fetchUser(@RequestParam int user_id,ModelMap model) {
			model.addAttribute("roleList", CoreService.getRolesMap());
			model.addAttribute("orgList", CoreService.getOrganisationsMap());
			return new ModelAndView("userUI", "command", CoreService.getUserFromID(user_id));
	   }
	
	@RequestMapping(value="/saveuser", method = RequestMethod.POST)
	public @ResponseBody
	String submitOwner(@ModelAttribute("SpringWeb")User user,HttpSession session,ModelMap model) {
		System.out.println("Session : " + (User)session.getAttribute("authuser"));	
			String trtx_id=CoreService.saveUser(user);
			return trtx_id;
	   }
	
	/** User Creation/Modification Ends  **/
	
	/** Reason Creation/Modification  **/
	
	@RequestMapping(value = "/getAllReasons", method = RequestMethod.GET)
	public String getReasons(HttpServletRequest request,ModelMap model) {
 		if(CoreService.isSessonActive(request)){
		model.addAttribute("responseTable", CoreService.getReasonsHtml(request));
		model.addAttribute("sessionStatus", "OK");
 		}else{
 			model.addAttribute("sessionStatus", "NOT_OK"); 
 		}
 		return "ownersReport";
 	}
	
	@RequestMapping(value="/showcreatereason", method = RequestMethod.GET)
	   public ModelAndView showReasonUI(ModelMap model) {
			return new ModelAndView("reasonUI", "command", new Reasons());
	   }
	
	@RequestMapping(value="/fetchreason", method = RequestMethod.GET)
	   public ModelAndView fetchReason(@RequestParam int reason_id,ModelMap model) {
			return new ModelAndView("reasonUI", "command", CoreService.getReasonFromID(reason_id));
	   }
	
	@RequestMapping(value="/savereason", method = RequestMethod.POST)
	public @ResponseBody
	String submitReason(@ModelAttribute("SpringWeb")Reasons reasons,HttpSession session,ModelMap model) {
		System.out.println("Session : " + (User)session.getAttribute("authuser"));	
			String trtx_id=CoreService.saveReason(reasons,((User)session.getAttribute("authuser")).getOrg_id());
			return trtx_id;
	   }
	
	/** Reason Creation/Modification Ends  **/
	
/** Transaction View  **/
	
	@RequestMapping(value="/showcredittxui", method = RequestMethod.GET)
	   public String showCreditTxUI(ModelMap model) {
			model.addAttribute("trtx_type", "CR");
			return "creditTxUI";
	   }
	
	@RequestMapping(value="/showdebittxui", method = RequestMethod.GET)
	   public String showDebitTxUI(ModelMap model) {
			model.addAttribute("trtx_type", "DR");
			return "creditTxUI";
	   }
	
	@RequestMapping(value="/showownertxui", method = RequestMethod.GET)
	   public String showOwnerTxUI(ModelMap model) {
			model.addAttribute("trtx_type", "ALL");
			return "ownerTxUI";
	   }
	
	@RequestMapping(value = "/getAllTransactions", method = RequestMethod.POST)
	public String getTransactions(@RequestParam String trtxType,@RequestParam String startDate,@RequestParam String endDate,@RequestParam int ownerId,HttpServletRequest request,ModelMap model) {
 		if(CoreService.isSessonActive(request)){
		model.addAttribute("responseTable", CoreService.getTransactionHtml(request, trtxType, startDate, endDate, ownerId));
		model.addAttribute("sessionStatus", "OK");
 		}else{
 			model.addAttribute("sessionStatus", "NOT_OK"); 
 		}
 		return "ownersReport";
 	}
	
	@RequestMapping(value="/getsummary", method = RequestMethod.GET)
	   public String showAccountSummaryUI(ModelMap model,HttpServletRequest request) {
			model.addAttribute("summary", CoreService.getAccountSummary(((User)request.getSession().getAttribute("authuser")).getOrg_id()));
			return "summary";
	   }
}
