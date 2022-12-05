package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.configuration.Generated;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Generated
@SpringBootApplication
 public class PayMyBuddyApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}


	@Override
	public void run(String... args) {

		System.out.println(" ______          ___  ___     ______           _     _       ");
		System.out.println(" | ___ \\         |  \\/  |     | ___ \\         | |   | |      ");
		System.out.println(" | |_/ /_ _ _   _| .  . |_   _| |_/ /_   _  __| | __| |_   _ ");
		System.out.println(" |  __/ _` | | | | |\\/| | | | | ___ \\ | | |/ _` |/ _` | | | |");
		System.out.println(" | | | (_| | |_| | |  | | |_| | |_/ / |_| | (_| | (_| | |_| |");
		System.out.println(" \\_|  \\__,_|\\__, \\_|  |_/\\__, \\____/ \\__,_|\\__,_|\\__,_|\\__, |");
		System.out.println(" =========== __/ |======= __/ |======================== __/ |");
		System.out.println("            |___/        |___/                         |___/ ");
		System.out.println(" :: WebApp ::                                        (v1.0.0)");
		System.out.println(" ");

	}
}
