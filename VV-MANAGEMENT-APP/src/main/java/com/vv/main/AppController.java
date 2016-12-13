package com.vv.main;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
		String baseTemplateName = "EnterExpenses";
        return baseTemplateName;
    }
	
}
