package it.exolab.dto;

import org.primefaces.model.file.UploadedFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Articolo {
	
	private Long id_articolo;
	private String titolo_articolo;
	private String descrizione_articolo;
	private String marchio;
	private String colore;
	private String indirizzo_immagine;
	private Integer quantita_disponibile;
	private Double prezzo_unitario;
	private Categoria categoriaDiAppartenenza;
	private UploadedFile imageFile;
	
	public Articolo() {
		categoriaDiAppartenenza = new Categoria();
	}

}
