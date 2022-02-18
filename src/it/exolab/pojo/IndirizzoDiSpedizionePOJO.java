package it.exolab.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndirizzoDiSpedizionePOJO {
	
	private Long id;
	private String scala;
	private String interno;
	private IndirizzoPOJO indirizzoDiRiferimento;
	private UtentePOJO utenteDiRiferimento;

}
