package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Projection implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateProjection;
	private double prix;
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Salle salle;
	@ManyToOne
	private Film film;
	@OneToMany(mappedBy = "projection")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Ticket> tickets;
	@ManyToOne
	private Seance seance;
}
