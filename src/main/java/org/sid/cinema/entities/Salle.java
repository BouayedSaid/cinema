package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;

import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class Salle implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int nombrePlace;
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Cinema cinema;
	@OneToMany(mappedBy = "salle")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Place> places;
	@OneToMany(mappedBy = "salle")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Projection> projections;
}
