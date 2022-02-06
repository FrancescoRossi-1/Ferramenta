package it.exolab.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Provincia implements Serializable {

	private static final long serialVersionUID = 5186569395759368166L;
	
	private Long id_province;
	private String nome_province;
	private String sigla_province;
	private String regione_province;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_province == null) ? 0 : id_province.hashCode());
		result = prime * result + ((nome_province == null) ? 0 : nome_province.hashCode());
		result = prime * result + ((regione_province == null) ? 0 : regione_province.hashCode());
		result = prime * result + ((sigla_province == null) ? 0 : sigla_province.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provincia other = (Provincia) obj;
		if (id_province == null) {
			if (other.id_province != null)
				return false;
		} else if (!id_province.equals(other.id_province))
			return false;
		if (nome_province == null) {
			if (other.nome_province != null)
				return false;
		} else if (!nome_province.equals(other.nome_province))
			return false;
		if (regione_province == null) {
			if (other.regione_province != null)
				return false;
		} else if (!regione_province.equals(other.regione_province))
			return false;
		if (sigla_province == null) {
			if (other.sigla_province != null)
				return false;
		} else if (!sigla_province.equals(other.sigla_province))
			return false;
		return true;
	}

	
}
