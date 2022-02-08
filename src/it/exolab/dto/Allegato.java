package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Allegato {
	
	private Long id_allegato;
	private String nome_file;
	private String estensione;
	private byte [] content;
	private Long id_articolo;

}
