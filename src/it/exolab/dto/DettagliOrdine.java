package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DettagliOrdine {
	
	private Long id;
	private Ordine ordineDiAppartenenza;
	private Articolo articoloDiAppartenenza;
	private Double prezzo;
	private Integer quantita;

}
