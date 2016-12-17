package com.vv.controller;

import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vv.exception.StorageFileNotFoundException;
import com.vv.model.Expense;
import com.vv.model.ExpenseFeedForm;
import com.vv.model.ExpenseRepository;
import com.vv.service.StorageService;
import com.vv.validator.FileValidator;

@Controller
public class ExpenseController extends WebMvcConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(ExpenseController.class);

	private StorageService storageService;
	private ExpenseRepository expenseRepo;

	public ExpenseController(StorageService storageService, ExpenseRepository expenseRepo) {
		this.storageService = storageService;
		this.expenseRepo = expenseRepo;
	}

	@Autowired
	private FileValidator fileValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
	    binder.addValidators(fileValidator);
	}


	@PostMapping("/registerExpense")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("expname") String expName,
			@RequestParam("expdetail") String expDetail, @RequestParam("expamt") String expAmt,
			RedirectAttributes redirectAttributes) {

		Stream<Expense> expense = Stream
				.of(new Expense(expName, expDetail, Integer.parseInt(expAmt), file.getOriginalFilename()));
		expense.forEach(expenseRepo::save);

		storageService.store(file);
		log.info(expense.toString());
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/registerExpenseNew")
	public String checkPersonInfo(@Valid ExpenseFeedForm entryForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		//fileValidator.validate(entryForm,bindingResult);
		if (bindingResult.hasErrors()) {
			return "EnterExpenses";
		}

		Stream<Expense> expense = Stream.of(new Expense(entryForm.getExpenseName(), entryForm.getExpenseExplain(),
				entryForm.getExpenseAmount(), entryForm.getProofFile().getOriginalFilename()));
		expense.forEach(expenseRepo::save);

		storageService.store(entryForm.getProofFile());
		  redirectAttributes.addFlashAttribute("message",
		  "You successfully uploaded " +
		  entryForm.getProofFile().getOriginalFilename() + "!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
