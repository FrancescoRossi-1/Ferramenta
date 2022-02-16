package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.CartaDiCredito;
import it.exolab.pojo.UtentePOJO;

public interface CartaDiCreditoMapper {

	static final String FIND_BY_USER_ID = " SELECT "
			+ " id_carte_di_credito, "
			+ " numero_carta, "
			+ " data_scadenza, "
			+ " cvv, "
			+ " nome_circuito, "
			+ " nominativo_proprietario, "
			+ " id_indirizzo_fatturazione "
			+ "FROM  "
			+ " CARTE_DI_CREDITO "
			+ "WHERE "
			+ " id_utente = #{id} ";

	static final String INSERT = " INSERT "
			+ "INTO  " 
			+ " CARTE_DI_CREDITO " 
			+ " ( " 
			+ " numero_carta, " 
			+ " data_scadenza, " 
			+ " CVV, " 
			+ " nominativo_proprietario, " 
			+ " id_indirizzo_fatturazione, " 
			+ " id_utente, " 
			+ " nome_circuito " 
			+ " ) " 
			+ "VALUES " 
			+ " ( " 
			+ " #{numero_carta}, " 
			+ " #{data_scadenza}, " 
			+ " #{CVV}, " 
			+ " #{nominativo_proprietario}, " 
			+ " #{id_indirizzo_fatturazione}, " 
			+ " #{id_utente}, " 
			+ " #{nome_circuito} " 
			+ " ) ";

	static final String DELETE_FROM_ID = " DELETE "  
			+ "FROM " 
			+ " CARTE_DI_CREDITO " 
			+ "WHERE " 
			+ " id_carte_di_credito = #{id_carte_di_credito} "; 

	@Select ( FIND_BY_USER_ID )
	@ResultType( CartaDiCredito.class )
	List<CartaDiCredito> findAllByUserId( UtentePOJO utente );

	@Insert ( INSERT )
	void insertCartaDiCredito(CartaDiCredito cartaDiCredito);

	@Delete ( DELETE_FROM_ID )
	void deleteCartaDiCreditoFromId(CartaDiCredito cartaDiCredito);

}
