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
	/*static Environment env;*/
	/*@Value("${property.dir}")
	String propertyDir;*/

	public static void main(String[] args)  {
		/*String someProperty= env.getProperty("someProperty");
		System.out.println("property	"+ someProperty);*/
		System.out.println("ARGS:");
		Arrays.stream(args).forEach(System.out::println);
		/*SpringApplication application = new SpringApplication(JavaUniversityApplication.class);
		application.setAddCommandLineProperties(false);
		application.run(args);*/
		SpringApplication.run(JavaUniversityApplication.class, args);
	}

/*	@Override
	public void run(ApplicationArguments args) throws Exception {
		boolean containsOption = args.containsOption("property.dir");
		System.out.println("Contains person.name: " + containsOption);
	}*/
}
