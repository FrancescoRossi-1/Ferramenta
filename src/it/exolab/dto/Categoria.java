package it.exolab.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Categoria implements Serializable {

	private static final long serialVersionUID = 4127560275264611574L;
	
	private Long id_categoria;
	private String nome_categoria;
	private String descrizione_categoria;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if( nome_categoria.equals(other.nome_categoria) ) {
			if( descrizione_categoria.equals(other.descrizione_categoria) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descrizione_categoria == null) ? 0 : descrizione_categoria.hashCode());
		result = prime * result + ((id_categoria == null) ? 0 : id_categoria.hashCode());
		result = prime * result + ((nome_categoria == null) ? 0 : nome_categoria.hashCode());
		return result;
	}
	
	
	
}
