package it.exolab.pojo;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrdineReport {

	private OrdinePOJO ordine;
	private List<DettagliOrdinePOJO> dettagliOrdine;
	
}
