package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.pojo.UtentePOJO;

public interface IndirizzoDiSpedizioneMapper {

	static String FIND_ALL_BY_USER_ID = " SELECT "  
			+ " id_indirizzo_spedizione, " 
			+ " scala, " 
			+ " interno, " 
			+ " id_indirizzo, " 
			+ " id_utente " 
			+ "FROM  " 
			+ " INDIRIZZI_DI_SPEDIZIONE " 
			+ "WHERE " 
			+ " id_utente = #{id_utente} ";
	
	static String FIND_ALL = " SELECT "
			+ " id_indirizzo_spedizione, " 
			+ " scala, " 
			+ " interno, " 
			+ " id_indirizzo, " 
			+ " id_utente " 
			+ "FROM  " 
			+ " INDIRIZZI_DI_SPEDIZIONE " ;
	
	@Select ( FIND_ALL_BY_USER_ID )
	@ResultType( IndirizzoDiSpedizione.class )
	public List<IndirizzoDiSpedizione> findAllByUserId(UtentePOJO utente);

	@Select ( FIND_ALL )
	public List<IndirizzoDiSpedizione> findAll();

}
