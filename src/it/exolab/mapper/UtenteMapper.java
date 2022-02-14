package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.exolab.dto.Carrello;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.Utente;
import it.exolab.pojo.UtentePOJO;

public interface UtenteMapper {


	static String INSERT_USER = " INSERT "  
			+ "INTO UTENTE "  
			+ " ( "
			+ " nome, " 
			+ " cognome, " 
			+ " codice_fiscale, "  
			+ " data_nascita, " 
			+ " email, " 
			+ " password, "  
			+ " id_indirizzo, "  
			+ " data_iscrizione, "  
			+ " isAdmin"
			+ " ) " 
			+ "VALUES " 
			+ " ( "
			+ " #{param1.nome}, "  
			+ " #{param1.cognome}, "  
			+ " #{param1.codice_fiscale}, "  
			+ " #{param1.data_nascita}, " 
			+ " #{param1.email}, " 
			+ " #{param1.password}, "  
			+ " #{param2.id_indirizzo}, "  
			+ " #{param1.data_iscrizione}, " 
			+ " #{param1.isAdmin}"
			+ " ) ";
	static String SELECT_FROM_EMAIL_AND_PASSWORD = "SELECT"  
			+ " id_utente, " 
			+ " nome, "  
			+ " cognome, "  
			+ " codice_fiscale, "  
			+ " data_nascita, "  
			+ " email, "  
			+ " password, "  
			+ " id_indirizzo, "  
			+ " data_iscrizione, "  
			+ " isAdmin "  
			+ "FROM "  
			+ " UTENTE "  
			+ "WHERE "  
			+ " email = #{email}"
			+ "AND"
			+ " password = #{password} ";

	static String SELECT_USER_JOIN_IND_PROV = " SELECT " 
			+ " u.id_utente, "  
			+ " u.nome, "  
			+ " u.cognome, "  
			+ " u.codice_fiscale, "  
			+ " u.data_nascita, "  
			+ " u.email, "  
			+ " u.password, "  
			+ " u.data_iscrizione, "  
			+ " u.isAdmin, "  
			+ " i.id_indirizzo, "  
			+ " i.via, "  
			+ " i.n_civico, "  
			+ " i.cap, "  
			+ " p.id_province, "  
			+ " p.nome_province, "  
			+ " p.sigla_province, "  
			+ " p.regione_province "  
			+ "FROM "  
			+ " utente u "  
			+ "JOIN "  
			+ " indirizzi i "  
			+ "ON "  
			+ " u.id_indirizzo = i.id_indirizzo "  
			+ "JOIN "  
			+ " province p " 
			+ "ON " 
			+ " i.id_provincia = p.id_province " 
			+ "WHERE " 
			+ " u.email = #{email} " 
			+ "AND " 
			+ " u.password = #{password} ";

	static String SELECT_ALL_USERS_JOIN_IND_PROV = " SELECT " 
			+ " u.id_utente, "  
			+ " u.nome, "  
			+ " u.cognome, "  
			+ " u.codice_fiscale, "  
			+ " u.data_nascita, "  
			+ " u.email, "  
			+ " u.password, "  
			+ " u.data_iscrizione, "  
			+ " u.isAdmin, "  
			+ " i.id_indirizzo, "  
			+ " i.via, "  
			+ " i.n_civico, "  
			+ " i.cap, "  
			+ " p.id_province, "  
			+ " p.nome_province, "  
			+ " p.sigla_province, "  
			+ " p.regione_province "  
			+ "FROM "  
			+ " utente u "  
			+ "JOIN "  
			+ " indirizzi i "  
			+ "ON "  
			+ " u.id_indirizzo = i.id_indirizzo "  
			+ "JOIN "  
			+ " province p " 
			+ "ON " 
			+ " i.id_provincia = p.id_province ";

	static String ASSEGNA_CARRELLO = " UPDATE "
			+ " UTENTE "
			+ "SET"
			+ " id_carrello = #{param1.id_carrello} "
			+ "WHERE " 
			+ " id_utente = #{param2.id} ";


	@Insert( INSERT_USER )
	void insertUser(Utente user, Indirizzo indirizzo);

	@Select( SELECT_FROM_EMAIL_AND_PASSWORD )
	@ResultType(Utente.class)
	Utente selectUserFromEmailAndPassword(Utente user);

	@Results(value = {
			@Result(property = "id", column = "id_utente", id = true),
			@Result(property = "nome", column = "nome"),
			@Result(property = "cognome", column = "cognome"),
			@Result(property = "codiceFiscale", column = "codice_fiscale"),
			@Result(property = "dataNascita", column = "data_nascita"),
			@Result(property = "email", column = "email"),
			@Result(property = "password", column = "password"),
			@Result(property = "dataIscrizione", column = "data_iscrizione"),
			@Result(property = "isAdmin", column = "isAdmin"),
			@Result(property = "indirizzoResidenza.idIndirizzo", column = "id_indirizzo", id = true),
			@Result(property = "indirizzoResidenza.via", column = "via"),
			@Result(property = "indirizzoResidenza.nCivico", column = "n_civico"),
			@Result(property = "indirizzoResidenza.cap", column = "cap"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.idProvincia", column = "id_province", id = true),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.nome", column = "nome_province"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.sigla", column = "sigla_province"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.regione", column = "regione_province"),
	})
	@Select ( SELECT_USER_JOIN_IND_PROV )
	@ResultType(UtentePOJO.class)
	UtentePOJO selectUser(Utente user);

	@Results(value = {
			@Result(property = "id", column = "id_utente", id = true),
			@Result(property = "nome", column = "nome"),
			@Result(property = "cognome", column = "cognome"),
			@Result(property = "codiceFiscale", column = "codice_fiscale"),
			@Result(property = "dataNascita", column = "data_nascita"),
			@Result(property = "email", column = "email"),
			@Result(property = "password", column = "password"),
			@Result(property = "dataIscrizione", column = "data_iscrizione"),
			@Result(property = "isAdmin", column = "isAdmin"),
			@Result(property = "indirizzoResidenza.idIndirizzo", column = "id_indirizzo", id = true),
			@Result(property = "indirizzoResidenza.via", column = "via"),
			@Result(property = "indirizzoResidenza.nCivico", column = "n_civico"),
			@Result(property = "indirizzoResidenza.cap", column = "cap"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.idProvincia", column = "id_province", id = true),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.nome", column = "nome_province"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.sigla", column = "sigla_province"),
			@Result(property = "indirizzoResidenza.provinciaAppartenente.regione", column = "regione_province"),
	})
	@Select ( SELECT_ALL_USERS_JOIN_IND_PROV )
	@ResultType(UtentePOJO.class)
	List<UtentePOJO> selectAllUtenti();

	@Update ( ASSEGNA_CARRELLO )
	void assegnaNuovoCarrello( Carrello carrelloUtente, UtentePOJO loggedUser );

}
