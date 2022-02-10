package it.exolab.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ordine implements Serializable {
	
	private static final long serialVersionUID = 6365810874070275329L;
	
	private Long id;
	private Utente proprietarioOrdine;
	private Double totaleDovuto;
	private Date dataOrdine;
	private String stato; //Ordinato , In spedizione , Spedito , Consegnato , Annullato
	
}
