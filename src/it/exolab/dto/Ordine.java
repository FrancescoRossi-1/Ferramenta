package it.exolab.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ordine implements Serializable {
	
	private static final long serialVersionUID = 6365810874070275329L;
	
	private Long id_ordine;
	private Long id_utente;
	private Double totale_ordine;
	private Date data_ordine;
	private String stato; //Ordinato , In spedizione , Spedito , Consegnato , Annullato
	private Long id_indirizzo_spedizione;
	private Long id_carta_credito;
	
}
