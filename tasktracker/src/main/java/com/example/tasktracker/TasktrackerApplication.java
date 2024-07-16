/**
 * Main class for the Task Tracker application.
 * This class initializes and runs the Spring Boot application.
 * @Autor Yevhenii Shatov
 */

package com.example.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasktrackerApplication {

	/**
	 * Main method to run the application.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TasktrackerApplication.class, args);
	}
}
