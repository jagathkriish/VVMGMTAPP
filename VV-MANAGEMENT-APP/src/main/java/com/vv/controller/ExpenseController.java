package com.vv.controller;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vv.exception.StorageFileNotFoundException;
import com.vv.model.Expense;
import com.vv.model.ExpenseRepository;
import com.vv.service.StorageService;

@Controller
public class ExpenseController {
	
	private final Logger log = LoggerFactory.getLogger(ExpenseController.class);
	
	private StorageService storageService;
	private ExpenseRepository expenseRepo;

	public ExpenseController(StorageService storageService,ExpenseRepository expenseRepo) {
		this.storageService  = storageService;
		this.expenseRepo = expenseRepo;
	}
	
	@PostMapping("/registerExpense")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("expname") String expName,@RequestParam("expdetail") String expDetail,@RequestParam("expamt") String expAmt,
                                   RedirectAttributes redirectAttributes) {
		
		Stream<Expense> expense =  Stream.of( new Expense(expName,expDetail,Integer.parseInt(expAmt),file.getOriginalFilename()));
		expense.forEach(expenseRepo::save);
		
        storageService.store(file);
        log.info(expense.toString());
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
	
	 @ExceptionHandler(StorageFileNotFoundException.class)
	    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
	        return ResponseEntity.notFound().build();
	 }
}
