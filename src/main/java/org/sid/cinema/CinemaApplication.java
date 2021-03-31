package org.sid.cinema;

import org.sid.cinema.service.ICinemaIntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaIntService cinemaIntService;
	
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		cinemaIntService.initVilles();
		cinemaIntService.initCinemas();
		cinemaIntService.initSalles();
		cinemaIntService.initPlaces();
		cinemaIntService.initSeances();
		cinemaIntService.initCategories();
		cinemaIntService.initFilms();
		cinemaIntService.initProjections();
		cinemaIntService.initTickets();
	}
}
