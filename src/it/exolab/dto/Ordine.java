package it.exolab.dto;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ordine {
	
	private Long id;
	private Utente proprietarioOrdine;
	private Double totaleDovuto;
	private Date dataOrdine;
	private String stato; //Ordinato , In spedizione , Spedito , Consegnato , Annullato
	
}
