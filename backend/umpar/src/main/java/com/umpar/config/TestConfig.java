package com.umpar.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.umpar.entities.Calcados;
import com.umpar.repositories.CalcadosRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private CalcadosRepository calcadosRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Calcados c1= new Calcados(null, "Sapato Social", "42", 129.99, "Marca X", "Preto", "Couro");
		
		Calcados c2= new Calcados(null, "Mocassim", "42", 322.99, "Marca T", "Branco", "Camurça");

		calcadosRepository.saveAll(Arrays.asList(c1,c2));
	}

}
