package it.exolab.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import it.exolab.dto.Ordine;

public interface OrdineMapper {

	static final String INSERT_ORDINE = " INSERT "
			+ "INTO " 
			+ " ORDINE " 
			+ " ( " 
			+ " id_ordine, " 
			+ " id_utente, " 
			+ " totale_ordine, " 
			+ " data_ordine, " 
			+ " stato, " 
			+ " id_indirizzo_spedizione, " 
			+ " id_carta_credito " 
			+ " ) " 
			+ "VALUES " 
			+ " ( " 
			+ " #{id_ordine}, " 
			+ " #{id_utente}, " 
			+ " #{totale_ordine}, " 
			+ " now() , " 
			+ " #{stato}, " 
			+ " #{id_indirizzo_spedizione}, " 
			+ " #{id_carta_credito} " 
			+ " ) ";
	
	@Insert( INSERT_ORDINE )
	@Options(useGeneratedKeys = true, keyProperty = "id_ordine", keyColumn = "id_ordine")
	void insertOrdine(Ordine ordine);

}
