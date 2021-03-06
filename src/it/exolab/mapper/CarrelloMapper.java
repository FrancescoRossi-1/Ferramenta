package it.exolab.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.exolab.dto.Carrello;
import it.exolab.pojo.UtentePOJO;

public interface CarrelloMapper {
	
	static final String FIND_BY_ID_UTENTE = " SELECT " 
			+ " c.id_carrello, " 
			+ " c.data_ultima_modifica, "  
			+ " c.totale_ordine " 
			+ "FROM " 
			+ " UTENTE u "  
			+ "JOIN " 
			+ " CARRELLI c " 
			+ "ON " 
			+ " u.id_carrello = c.id_carrello "
			+ "WHERE "
			+ " u.id_utente = #{id}";
	
	static final String CREATE_NEW_CARRELLO = " INSERT "
			+ "INTO"
			+ " carrelli "
			+ " ( "
			+ " data_ultima_modifica, "
			+ " totale_ordine "
			+ " ) "
			+ "VALUES "
			+ " ( "
			+ " now(), "
			+ " 0 "
			+ " ) " ;
	
	static final String FIND_BY_ID = " SELECT "
			+ " id_carrello, "
			+ " data_ultima_modifica, "
			+ " totale_ordine "
			+ "FROM "
			+ " CARRELLI "
			+ "WHERE "
			+ " id_carrello = #{id_carrello} ";
	
	static final String UPDATE_TOTALE_E_ULTIMA_MODIFICA = " UPDATE " 
 			+ " CARRELLI c " 
			+ "JOIN  " 
			+ " ( " 
			+ " SELECT " 
			+ "  id_carrello, "
			+ " ROUND( SUM( prezzo_unitario * quantita ) ,  2) as prezzototale " 
			+ " FROM " 
			+ "  CARRELLO_ARTICOLO carart " 
			+ " JOIN " 
			+ "  ARTICOLI a " 
			+ " ON  " 
			+ "  a.id_articolo = carart.id_articolo " 
			+ " GROUP BY " 
			+ "  id_carrello " 
			+ " ) as prezzo  " 
			+ "ON  " 
			+ " c.id_carrello = prezzo.id_carrello " 
			+ "SET  " 
			+ " c.totale_ordine = prezzo.prezzototale, " 
			+ " c.data_ultima_modifica = now() " 
			+ "WHERE " 
			+ " c.id_carrello = #{id_carrello} ";
	
	static final String DELETE_FROM_ID = " DELETE "
			+ "FROM " 
			+ " CARRELLI " 
			+ "WHERE " 
			+ " id_carrello = id_carrello ";
	
	@Select ( FIND_BY_ID_UTENTE )
	@ResultType ( Carrello.class )
	Carrello findByIdUtente(UtentePOJO utente);

	@Insert ( CREATE_NEW_CARRELLO )
	@Options(useGeneratedKeys = true, keyProperty = "carrello.id_carrello", keyColumn = "id_carrello")
	void createNewCarrello( @Param("carrello") Carrello carrello);

	@Select( FIND_BY_ID )
	@ResultType ( Carrello.class )
	Carrello findByIdCarello(Carrello carrello);

	@Update ( UPDATE_TOTALE_E_ULTIMA_MODIFICA )
	void updateTotaleEUltimaModifica(Carrello carrello);

	@Delete ( DELETE_FROM_ID )
	void deleteFromUserId(Carrello carrello);

}
