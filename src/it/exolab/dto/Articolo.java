package it.exolab.dto;

import java.util.ArrayList;
import java.util.List;

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
	private Integer quantita_disponibile;
	private Double prezzo_unitario;
	private Categoria categoriaDiAppartenenza;
	private List<Allegato> allegatiAppartenenti;
	
	public Articolo() {
		categoriaDiAppartenenza = new Categoria();
		allegatiAppartenenti = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Articolo other = (Articolo) obj;
			if(titolo_articolo.equals(other.titolo_articolo)) {
				if(descrizione_articolo.equals(other.descrizione_articolo)) {
					if(marchio.equals(other.marchio)){
						if(colore.equals(other.colore)) {
							if(prezzo_unitario.equals(other.prezzo_unitario)) {
								if(categoriaDiAppartenenza.getId_categoria() == other.categoriaDiAppartenenza.getId_categoria()) {									
									return true;
								}
							}
						}
					}
				}
			}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegatiAppartenenti == null) ? 0 : allegatiAppartenenti.hashCode());
		result = prime * result + ((categoriaDiAppartenenza == null) ? 0 : categoriaDiAppartenenza.hashCode());
		result = prime * result + ((colore == null) ? 0 : colore.hashCode());
		result = prime * result + ((descrizione_articolo == null) ? 0 : descrizione_articolo.hashCode());
		result = prime * result + ((id_articolo == null) ? 0 : id_articolo.hashCode());
		result = prime * result + ((marchio == null) ? 0 : marchio.hashCode());
		result = prime * result + ((prezzo_unitario == null) ? 0 : prezzo_unitario.hashCode());
		result = prime * result + ((quantita_disponibile == null) ? 0 : quantita_disponibile.hashCode());
		result = prime * result + ((titolo_articolo == null) ? 0 : titolo_articolo.hashCode());
		return result;
	}
	
	

}
