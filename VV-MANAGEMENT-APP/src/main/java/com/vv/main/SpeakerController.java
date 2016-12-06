package com.vv.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpeakerController {
	private SpeakerRepository repository;

	public SpeakerController(SpeakerRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/speakers")
	public String list(Model model){
		model.addAttribute("speakers", this.repository.findAll());
		return "speakers";
	}
	
}
