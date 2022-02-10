package it.exolab.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DettagliOrdine implements Serializable {

	private static final long serialVersionUID = 3097244447081844060L;
	
	private Long id;
	private Ordine ordineDiAppartenenza;
	private Articolo articoloDiAppartenenza;
	private Double prezzo;
	private Integer quantita;

}
