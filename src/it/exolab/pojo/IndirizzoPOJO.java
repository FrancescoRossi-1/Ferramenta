package it.exolab.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndirizzoPOJO {
	
	private Long idIndirizzo;
	private String via;
	private String nCivico;
	private String cap;
	private ProvinciaPOJO provinciaAppartenente;

}
