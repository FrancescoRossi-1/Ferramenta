package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Categoria {

	private Long id_categoria;
	private String nome_categoria;
	private String descrizione_categoria;
	
}
