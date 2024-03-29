package com.agenciakriativa.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.agenciakriativa.cursomc.services.DbService;
import com.agenciakriativa.cursomc.services.EmailService;
import com.agenciakriativa.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	
	@Autowired
	private DbService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instantianteTesteDatabase();
		return true;
	} 
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	

}
