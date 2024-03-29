package org.sid.cinema.service;

import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Categorie;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Seance;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class ICinemaIntServiceImpl implements ICinemaIntService {
	
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		Stream.of("Casablanca", "Marrakech", "Tanger").forEach(nameVille -> {
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
	}
	
	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v -> {
			Stream.of("Megarama", "IMAX", "Opera").forEach(nameCinema -> {
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalle(3 + (int)Math.random() * 7);
				cinema.setVille(v);
				cinemaRepository.save(cinema);
			});
		});
	}
	
	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema -> {
			for (int i = 0; i < cinema.getNombreSalle(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle " + (i + 1));
				salle.setCinema(cinema);
				salle.setNombrePlace(15 + (int)(Math.random() * 20));
				salleRepository.save(salle);
			}
		});
	}
	
	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle -> {
			for (int i = 0; i < salle.getNombrePlace(); i++) {
				Place place = new Place();
				place.setNumero(i + 1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
	}
	
	@Override
	public void initSeances() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(df.parse(s));
				seanceRepository.save(seance);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
			
		});
	}
	
	@Override
	public void initCategories() {
		Stream.of("Histoire", "Actions", "Fiction", "Drama").forEach(c -> {
			Categorie categorie = new Categorie();
			categorie.setName(c);
			categorieRepository.save(categorie);
		});
	}
	
	@Override
	public void initFilms() {
		double[] durees = new double[] { 1, 1.5, 2, 2.5, 3 };
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Titanic", "3 idiots", "PK").forEach(nameFilm -> {
			Film film = new Film();
			film.setTitre(nameFilm);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(nameFilm.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
	}
	
	@Override
	public void initProjections() {
		double[] prices = new double[] { 30, 35, 50, 60, 100 };
		villeRepository.findAll().forEach(ville -> {
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					filmRepository.findAll().forEach(film -> {
						seanceRepository.findAll().forEach(seance -> {
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setSalle(salle);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setFilm(film);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
					});
				});
			});
		});
	}
	
	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p -> {
			p.getSalle().getPlaces().forEach(place -> {
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setProjection(p);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
	}
}
