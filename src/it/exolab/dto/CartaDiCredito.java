package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartaDiCredito {
	
	private Long id_carte_di_credito;
	private String numeroCarta;
	private String data_scadenza;
	private String CVV;
	private String nominativoProprietario;
	private Long id_utente;
	private Long id_indirizzo_fatturazione;

}
