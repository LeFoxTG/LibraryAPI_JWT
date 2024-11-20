package com.apiRestProject.acm;

import com.apiRestProject.acm.persistence.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

@SpringBootApplication
public class AcmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcmApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializePasswords(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			usuarioRepository.findAll().forEach(usuario -> {
				if (!usuario.getPassword().startsWith("$2a$")) {
					usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
					usuarioRepository.save(usuario);
				}
			});
		};
	}

}
