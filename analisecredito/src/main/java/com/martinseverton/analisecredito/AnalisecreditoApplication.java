package com.martinseverton.analisecredito;

import com.martinseverton.analisecredito.service.AnaliseCreditoService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@SpringBootApplication
public class AnalisecreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalisecreditoApplication.class, args);
	}
}
