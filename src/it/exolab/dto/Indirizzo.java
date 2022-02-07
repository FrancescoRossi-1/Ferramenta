package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Indirizzo {

	private Long id_indirizzo;
	private String via;
	private String n_civico;
	private String cap;
	private Provincia provinciaDiAppartenenza;

	public Indirizzo() {
		provinciaDiAppartenenza = new Provincia();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
		result = prime * result + ((id_indirizzo == null) ? 0 : id_indirizzo.hashCode());
		result = prime * result + ((n_civico == null) ? 0 : n_civico.hashCode());
		result = prime * result + ((provinciaDiAppartenenza == null) ? 0 : provinciaDiAppartenenza.hashCode());
		result = prime * result + ((via == null) ? 0 : via.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indirizzo other = (Indirizzo) obj;
		if(via.contentEquals(other.via)) {
			if(n_civico.equals(other.n_civico)) {
				if(cap.equals(other.cap)) {
					if(provinciaDiAppartenenza.equals(other.provinciaDiAppartenenza)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
