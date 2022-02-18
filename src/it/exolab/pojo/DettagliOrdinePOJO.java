package it.exolab.pojo;
 
import it.exolab.dto.Ordine;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DettagliOrdinePOJO {

	private Long id;
	private Integer quantitaArticolo;
	private ArticoloPOJO articoloAcquistato;
	private Ordine ordineDiRiferimento;
	
}
