package com.vv.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.vv.config.AppProperties;
import com.vv.model.ExpenseRepository;


@SpringBootApplication
@ComponentScan(basePackages={"com.vv.config","com.vv.model","com.vv.main","com.vv.controller","com.vv.exception","com.vv.service","com.vv.validator"})
@EntityScan(basePackages={"com.vv.model"})
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories(basePackages={"com.vv.model"})
public class Application {
	
	private Logger log = LoggerFactory.getLogger(Application.class);
		
	@Autowired
	private AppProperties properties;

	private ExpenseRepository exp_repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public Application(AppProperties properties,ExpenseRepository exp_repository) {
		//this.repository = repository;
		this.properties = properties;
		this.exp_repository = exp_repository;
	}
	
	@Bean
	public CommandLineRunner messages(){
		return (args)->{
			log.info(properties.getFileLocation());
		};
	}
	
	@Bean
	public CommandLineRunner expenses(){
		return (args)->{
			this.exp_repository.findAll();
		};
	}
	
	@Bean
	public HealthIndicator springOneHealthIndicator(){
		return ()->new Health.Builder().up().withDetail("message","SpringBootApp is running").build();
	}

}
