package com.vv.main;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.vv.utils.StorageService;


@SpringBootApplication
@ComponentScan({"com.vv.main", "com.vv.utils"})
@EnableConfigurationProperties(AppProperties.class)
public class Application {
	
	private SpeakerRepository repository;
	
	private AppProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public Application(SpeakerRepository repository,AppProperties properties) {
		this.repository = repository;
		this.properties = properties;
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
