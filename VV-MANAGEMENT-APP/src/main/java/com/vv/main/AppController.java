package com.vv.main;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vv.model.Expense;
import com.vv.model.ExpenseRepository;
import com.vv.utils.StorageService;

@Controller
public class AppController {
	
	private StorageService storageService;
	private ExpenseRepository expenseRepo;

	public AppController(StorageService storageService,ExpenseRepository expenseRepo) {
		this.storageService  = storageService;
		this.expenseRepo = expenseRepo;
	}

	@GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        return "EnterExpenses";
    }
	
	@PostMapping("/registerExpense")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("expname") String expName,@RequestParam("expdetail") String expDetail,@RequestParam("expamt") String expAmt,
                                   RedirectAttributes redirectAttributes) {
		
		Stream<Expense> expense =  Stream.of( new Expense(expName,expDetail,Integer.parseInt(expAmt),file.getOriginalFilename()));
		expense.forEach(expenseRepo::save);
		
        storageService.store(file);
        System.out.println(expense);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
