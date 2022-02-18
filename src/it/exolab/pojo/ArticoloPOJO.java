package it.exolab.pojo;

import it.exolab.dto.Categoria;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ArticoloPOJO {

	private Long id;
	private String titolo;
	private String descrizione;
	private String marchio;
	private String colore;
	private Integer quantitaDisponibile;
	private Double prezzoUnitario;
	private Categoria categoriaDiAppartenenza;
	
}
