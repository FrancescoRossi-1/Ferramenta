package it.exolab.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Allegato implements Serializable {
	
	private static final long serialVersionUID = -4164240376922321443L;
	
	private Long id_allegato;
	private String nome_file;
	private String estensione;
	private byte [] content;
	private Long id_articolo;

}
