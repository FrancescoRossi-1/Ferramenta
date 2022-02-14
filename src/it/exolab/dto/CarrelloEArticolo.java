 package it.exolab.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarrelloEArticolo implements Serializable{
	
	private static final long serialVersionUID = 7780389911281938821L;
	
	private Long id_carrello_articolo;
	private Long id_carrello;
	private Long id_articolo;
	private Integer quantita;

}
