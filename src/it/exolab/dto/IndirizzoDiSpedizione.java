package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndirizzoDiSpedizione {

	private Long id_indirizzo_spedizione;
	private String scala;
	private String interno;
	private Long id_indirizzo;
	private Long id_utente;


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndirizzoDiSpedizione other = (IndirizzoDiSpedizione) obj;
		if(scala.equals(other.scala)) {
			if(interno.equals(other.interno)) {
				if(id_indirizzo.equals(other.id_indirizzo)) {
					if(id_utente.equals(other.id_utente)) {
						return true;
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
		result = prime * result + ((id_indirizzo == null) ? 0 : id_indirizzo.hashCode());
		result = prime * result + ((id_indirizzo_spedizione == null) ? 0 : id_indirizzo_spedizione.hashCode());
		result = prime * result + ((id_utente == null) ? 0 : id_utente.hashCode());
		result = prime * result + ((interno == null) ? 0 : interno.hashCode());
		result = prime * result + ((scala == null) ? 0 : scala.hashCode());
		return result;
	}



}
