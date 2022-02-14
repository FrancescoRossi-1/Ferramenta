package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.CartaDiCredito;
import it.exolab.pojo.UtentePOJO;

public interface CartaDiCreditoMapper {
	
	static String FIND_BY_USER_ID = " SELECT "
			+ " id_carte_di_credito, "
			+ " numero_carta, "
			+ " data_scadenza, "
			+ " cvv, "
			+ " nominativo_proprietario, "
			+ " id_indirizzo_fatturazione "
			+ "FROM  "
			+ " CARTE_DI_CREDITO "
			+ "WHERE "
			+ " id_utente = #{id} ";
	
	@Select ( FIND_BY_USER_ID )
	@ResultType( CartaDiCredito.class )
	List<CartaDiCredito> findAllByUserId( UtentePOJO utente );

}
