package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.pojo.UtentePOJO;

public interface IndirizzoDiSpedizioneMapper {

	static final String FIND_ALL_BY_USER_ID = " SELECT "  
			+ " id_indirizzo_spedizione, " 
			+ " scala, " 
			+ " interno, " 
			+ " id_indirizzo, " 
			+ " id_utente " 
			+ "FROM  " 
			+ " INDIRIZZI_DI_SPEDIZIONE " 
			+ "WHERE " 
			+ " id_utente = #{id} ";
	
	static final String FIND_ALL = " SELECT "
			+ " id_indirizzo_spedizione, " 
			+ " scala, " 
			+ " interno, " 
			+ " id_indirizzo, " 
			+ " id_utente " 
			+ "FROM  " 
			+ " INDIRIZZI_DI_SPEDIZIONE " ;
	
	static final String INSERT = " INSERT " 
			+ "INTO "
			+ " INDIRIZZI_DI_SPEDIZIONE "
			+ " ( "
			+ " scala, "
			+ " interno, "
			+ " id_indirizzo, "
			+ " id_utente "
			+ " ) "
			+ "VALUES "
			+ " ( "
			+ " #{param1.scala}, "
			+ " #{param1.interno}, "
			+ " #{param1.id_indirizzo}, "
			+ " #{param2.id} "
			+ " ) ";
	
	static final String DELETE_FROM_ID = " DELETE "
			+ "FROM " 
			+ " INDIRIZZI_DI_SPEDIZIONE " 
			+ "WHERE  " 
			+ " id_indirizzo_spedizione = #{id_indirizzo_spedizione} ";
	
	@Select ( FIND_ALL_BY_USER_ID )
	public List<IndirizzoDiSpedizione> findAllByUserId(UtentePOJO utente);

	@Select ( FIND_ALL )
	public List<IndirizzoDiSpedizione> findAll();

	@Insert ( INSERT )
	public void insertIndirizzoSpedizione(IndirizzoDiSpedizione indirizzoSpedizione, UtentePOJO utente);

	@Delete ( DELETE_FROM_ID )
	public void deleteIndirizzoDiSpedizioneFromId(IndirizzoDiSpedizione indirizzoDiSpedizione);

}
