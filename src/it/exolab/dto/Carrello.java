package it.exolab.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Carrello implements Serializable {
	
	private static final long serialVersionUID = 7484554736553765660L;
	
	private Long id_carrello;
	private Date data_ultima_modifica;
	private Double totale_ordine;
	
}
