package it.exolab.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import it.exolab.dto.Articolo;
import it.exolab.dto.Ordine;

public interface DettagliOrdineMapper {
	
	static final String INSERT_DETTAGLI_ORDINE = "<script>"
			+ "INSERT  " 
			+ "INTO " 
			+ " DETTAGLI_ORDINE " 
			+ " (id_ordine, " 
			+ " id_articolo, " 
			+ " quantita) " 
			+ "VALUES " 
			+ "<foreach collection=\"entries.entrySet()\" index=\"index\" item=\"item\" separator=\" , \">"
			+ " ( " 
			+ " #{param1.id_ordine}, " 
			+ " #{index.id_articolo}, " 
			+ " #{item.value} " 
			+ " ) "
			+ "	</foreach> "
			+ "</script>" ;

	@Insert( INSERT_DETTAGLI_ORDINE )
	void insertDettagliOrdine(Ordine ordine, @Param("entries") HashMap<Articolo, Integer> articoliEQuantita);

}
