package com.vv.main;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vv.model.Speaker;
import com.vv.model.SpeakerRepository;

@RestController("/speakerData")
public class SpeakerRestController {
	private SpeakerRepository repository;

	public SpeakerRestController(SpeakerRepository repository) {
		super();
		this.repository = repository;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/speakersList")
	public List<Speaker> list(){
		return this.repository.findAll();
	}
	
}
