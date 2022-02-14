package it.exolab.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Utente implements Serializable {

	private static final long serialVersionUID = -7146299757998337125L;
	private Long id_utente;
	private String nome;
	private String cognome;
	private String codice_fiscale;
	private Date data_nascita;
	private String email;
	private String password;
	private Long id_indirizzo;
	private Date data_iscrizione;
	private Boolean isAdmin;

	public Utente() {
		isAdmin = false;
	}

}
