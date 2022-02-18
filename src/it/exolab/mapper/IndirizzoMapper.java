package it.exolab.mapper;

import it.exolab.dto.Indirizzo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;

public interface IndirizzoMapper extends ProvinciaMapper {
	
	static String SELECT_ALL_INDIRIZZI = " SELECT "
			+ " id_indirizzo, " 
			+ " via, " 
			+ " n_civico, " 
			+ " cap, " 
			+ " id_provincia " 
			+ "FROM " 
			+ " INDIRIZZI ";

	static String INSERT_INDIRIZZO = " INSERT "  
			+ "INTO indirizzi "  
			+ " (via, " 
			+ " n_civico, "  
			+ " cap, " 
			+ " id_provincia) " 
			+ "VALUES " 
			+ " (#{param1.via}, "  
			+ " #{param1.n_civico},  " 
			+ " #{param1.cap}, "  
			+ " #{param2} ) ";
	


	@Insert( INSERT_INDIRIZZO )
	@Options(useGeneratedKeys = true, keyProperty = "param1.id_indirizzo", keyColumn = "id_indirizzo")
	void insertAddress(Indirizzo indirizzoResidenza, Long idProvincia);

	@Select ( SELECT_ALL_INDIRIZZI )
	@ResultType ( Indirizzo.class )
	List<Indirizzo> findAllIndirizzi();

}
