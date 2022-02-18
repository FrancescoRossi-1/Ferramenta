package it.exolab.pojo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrdinePOJO {
	
	private Long id;
	private UtentePOJO acquirente;
	private Double totaleOrdine;
	private Date dataOrdine;
	private String stato;
	private IndirizzoDiSpedizionePOJO indirizzoDiSpedizione;
	private CartaDiCreditoPOJO cartaDiCredito;
	private Date dataConsegna;

}
