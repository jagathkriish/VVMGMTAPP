package com.vv.main;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/speakerData")
public class SpeakerRestController {
	private SpeakerRepository repository;

	public SpeakerRestController(SpeakerRepository repository) {
		super();
		this.repository = repository;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping
	public List<Speaker> list(){
		return this.repository.findAll();
	}
	
	
}
