package com.mmoi.javauniversity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class JavaUniversityApplication {

	public static void main(String[] args)  {

		System.out.println("ARGS:");
		Arrays.stream(args).forEach(System.out::println);

		SpringApplication.run(JavaUniversityApplication.class, args);
	}

}
