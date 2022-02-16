package it.exolab.pojo;

import it.exolab.dto.Indirizzo;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartaDiCreditoPOJO {
	
	private Long id;
	private String numeroCarta;
	private String dataScadenza;
	private String CVV;
	private String nominativoProprietario;
	private String nomeCircuito;
	private UtentePOJO utenteDiRiferimento;
	private Indirizzo indirizzoFatturazione;
	
}
