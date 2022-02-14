package it.exolab.pojo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UtentePOJO {
	
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataNascita;
	private String email;
	private String password;
	private Date dataIscrizione;
	private Boolean isAdmin;
	private IndirizzoPOJO indirizzoResidenza;

}
