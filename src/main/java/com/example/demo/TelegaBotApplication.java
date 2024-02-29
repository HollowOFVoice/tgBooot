package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
public class TelegaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegaBotApplication.class, args);
	}
//7140094007:AAEGcx5uoXWifLl9QEPm7M5juMLcxzhvSG4
}
