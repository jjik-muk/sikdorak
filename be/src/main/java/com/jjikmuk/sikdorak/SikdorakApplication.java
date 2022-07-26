package com.jjikmuk.sikdorak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SikdorakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SikdorakApplication.class, args);
	}

}
