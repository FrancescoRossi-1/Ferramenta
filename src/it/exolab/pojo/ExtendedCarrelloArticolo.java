package it.exolab.pojo;

import java.util.Map;

import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExtendedCarrelloArticolo {
	
	private Carrello carrello;
	private Map<Articolo,Integer> articoloEQuantita;

}
