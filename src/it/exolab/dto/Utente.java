package it.exolab.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Utente {

	private Long id_utente;
	private String nome;
	private String cognome;
	private String codice_fiscale;
	private Date data_nascita;
	private String email;
	private String password;
	private Indirizzo indirizzoResidenza;
	private Date data_iscrizione;
	private Boolean isAdmin;

	public Utente() {
		indirizzoResidenza = new Indirizzo();
		isAdmin = false;
	}

}
