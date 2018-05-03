package net.formula97.springapp.skeltonapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class SkeltonappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkeltonappApplication.class, args);
	}
}
