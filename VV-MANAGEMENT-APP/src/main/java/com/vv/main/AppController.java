package com.vv.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.vv.model.ExpenseFeedForm;

@Controller
public class AppController {
	
	@GetMapping("/")
    public String showExpenseEntryForm(ExpenseFeedForm expenseFeedForm) {
		String baseTemplateName = "EnterExpenses";
        return baseTemplateName;
    }

	/*@GetMapping("/old")
    public String viewHomePage(Model model) throws IOException {
		String baseTemplateName = "EnterExpenses";
        return baseTemplateName;
    }*/
	
}
