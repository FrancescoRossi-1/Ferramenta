package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import it.exolab.dto.CarrelloEArticolo;

public interface CarrelloEArticoloMapper {

	static String INSERT_RIGA = " INSERT "
			+ "INTO "
			+ " CARRELLO_ARTICOLO "
			+ " ( "
			+ " id_articolo, "
			+ " quantita, "
			+ " id_carrello "
			+ " ) "
			+ "VALUES "
			+ " ( "
			+ " #{param1.id_articolo}, "
			+ " #{param2}, "
			+ " #{param3.id_carrello}"
			+ " ) ";
	
	static String SELECT_FROM_ID_CARRELLO = " SELECT " 
			+ " carart.id_carrello_articolo, "  
			+ " carart.id_carrello, "  
			+ " carart.id_articolo, "  
			+ " carart.quantita "  
			+ "FROM "  
			+ " CARRELLO_ARTICOLO carart "  
			+ "JOIN "  
			+ " CARRELLI c "  
			+ "ON  "  
			+ " carart.id_carrello = c.id_carrello "  
			+ "WHERE "  
			+ " c.id_carrello = #{id_carrello} ";
	
	static String DELETE_FROM_IDS = " DELETE " 
			+ "FROM  "
			+ " CARRELLO_ARTICOLO  "
			+ "WHERE  "
			+ " id_carrello = #{param1.id_carrello}  "
			+ "AND  "
			+ " id_articolo = #{param2.id_articolo} ";
	
	@Insert( INSERT_RIGA )
	void insertRiga(Articolo articolo, Integer quantita, Carrello carrello);

	@Select ( SELECT_FROM_ID_CARRELLO )
	@ResultType( CarrelloEArticolo.class )
	List<CarrelloEArticolo> selectRigheFromIdCarrello(Carrello carrello);

	@Delete ( DELETE_FROM_IDS )
	void deleteRigaFromIds(Carrello carrello, Articolo articolo);



}
