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

}
