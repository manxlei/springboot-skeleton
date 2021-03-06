package net.manxlei.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.manxlei.springboot.web.service.CreditService;

@Controller
public class CreditController {

	@Autowired
	CreditService service;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping(value = "/list-credits", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String name = getLoggedInUserName(model);
		model.put("credits", service.retrieveCustomers(name));
		return "list-credits";
	}
	
//	@RequestMapping(value = "/list-waiting", method = RequestMethod.GET)
////	public String showWaiting(ModelMap model) {
////		return "list-waiting";
////	}
	
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String showPersonal(ModelMap model) {

		getLoggedInUserName(model);
		
		return "personal";
	}

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

//	@RequestMapping(value = "/add-credit", method = RequestMethod.GET)
//	public String showAddTodoPage(ModelMap model) {
//		model.addAttribute("todo", new Todo(0, getLoggedInUserName(model),
//				"Default Desc", new Date(), false));
//		return "credit";
//	}
//
//	@RequestMapping(value = "/delete-credit", method = RequestMethod.GET)
//	public String deleteTodo(@RequestParam int id) {
//
//		if (id == 1)
//			throw new RuntimeException("Something went wrong");
//
//		service.deleteTodo(id);
//		return "redirect:/list-credits";
//	}

//	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
//	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
//		Todo todo = service.retrieveTodo(id);
//		model.put("todo", todo);
//		return "todo";
//	}

//	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
//	public String updateTodo(ModelMap model, @Valid Todo todo,
//			BindingResult result) {
//
//		if (result.hasErrors()) {
//			return "todo";
//		}
//
//		todo.setUser(getLoggedInUserName(model));
//
//		service.updateTodo(todo);
//
//		return "redirect:/list-credits";
//	}

//	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
//	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
//
//		if (result.hasErrors()) {
//			return "todo";
//		}
//
//		service.addTodo(getLoggedInUserName(model), todo.getDesc(),
//				todo.getTargetDate(), false);
//		return "redirect:/list-credits";
//	}
}
