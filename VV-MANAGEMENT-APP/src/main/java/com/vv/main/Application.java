package com.vv.main;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vv.model.SpeakerRepository;
import com.vv.model.ExpenseRepository;
import com.vv.model.Speaker;


@SpringBootApplication
@ComponentScan(basePackages={"com.vv.utils","com.vv.model","com.vv.main","com.vv.controller"})
@EntityScan(basePackages={"com.vv.model"})
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories(basePackages={"com.vv.model"})
public class Application {
	
	private SpeakerRepository repository;
	
	private AppProperties properties;

	private ExpenseRepository exp_repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public Application(SpeakerRepository repository,AppProperties properties,ExpenseRepository exp_repository) {
		this.repository = repository;
		this.properties = properties;
		this.exp_repository = exp_repository;
	}

	@Bean
	public CommandLineRunner loadSpeakers(){
		return (args)->{
			Stream<Speaker> speakers =  Stream.of( new Speaker("Jagath kriish"), new Speaker("venkat kriish"));
			speakers.forEach(repository::save);
		};
	}
	
	@Bean
	public CommandLineRunner messages(){
		return (args)->{
			System.out.println(properties.getName());
			System.out.println(properties.getLocation());
		};
	}
	
	@Bean
	public CommandLineRunner expenses(){
		return (args)->{
			this.exp_repository.findAll();
		};
	}
	
	@Bean
	public HealthIndicator SpringOneHealthIndicator(){
		return ()->new Health.Builder().up().withDetail("message","SpringBootApp is running").build();
	}
	
	/*@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            //storageService.deleteAll();
            //storageService.init();
		};
	}*/
}
