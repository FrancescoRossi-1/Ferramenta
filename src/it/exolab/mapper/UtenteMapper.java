package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.exolab.dto.Carrello;
import it.exolab.dto.Utente;

public interface UtenteMapper {
	
	static String SELECT_USER_FROM_EMAIL = "SELECT"  
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
			+ " email = #{email} ";
	
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
			+ " #{nome}, "  
			+ " #{cognome}, "  
			+ " #{codice_fiscale}, "  
			+ " #{data_nascita}, " 
			+ " #{email}, " 
			+ " #{password}, "  
			+ " #{indirizzoResidenza.id_indirizzo}, "  
			+ " #{data_iscrizione}, " 
			+ " #{isAdmin}"
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
			+ " id_utente = #{param2.id_utente} ";

	@Select( SELECT_USER_FROM_EMAIL )
	@ResultType(Utente.class)
	Utente selectUserFromEmail(Utente user);
	
	@Insert( INSERT_USER )
	void insertUser(Utente user);

	@Select( SELECT_FROM_EMAIL_AND_PASSWORD )
	@ResultType(Utente.class)
	Utente selectUserFromEmailAndPassword(Utente user);

	@Results(value = {
		@Result(property = "id_utente", column = "id_utente", id = true),
		@Result(property = "nome", column = "nome"),
		@Result(property = "cognome", column = "cognome"),
		@Result(property = "codice_fiscale", column = "codice_fiscale"),
		@Result(property = "data_nascita", column = "data_nascita"),
		@Result(property = "email", column = "email"),
		@Result(property = "password", column = "password"),
		@Result(property = "data_iscrizione", column = "data_iscrizione"),
		@Result(property = "isAdmin", column = "isAdmin"),
		@Result(property = "indirizzoResidenza.id_indirizzo", column = "id_indirizzo", id = true),
		@Result(property = "indirizzoResidenza.via", column = "via"),
		@Result(property = "indirizzoResidenza.n_civico", column = "n_civico"),
		@Result(property = "indirizzoResidenza.cap", column = "cap"),
		@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.id_province", column = "id_province", id = true),
		@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.nome_province", column = "nome_province"),
		@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.sigla_province", column = "sigla_province"),
		@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.regione_province", column = "regione_province"),
	})
	@Select ( SELECT_USER_JOIN_IND_PROV )
	@ResultType(Utente.class)
	Utente selectUser(Utente user);

	@Results(value = {
			@Result(property = "id_utente", column = "id_utente", id = true),
			@Result(property = "nome", column = "nome"),
			@Result(property = "cognome", column = "cognome"),
			@Result(property = "codice_fiscale", column = "codice_fiscale"),
			@Result(property = "data_nascita", column = "data_nascita"),
			@Result(property = "email", column = "email"),
			@Result(property = "password", column = "password"),
			@Result(property = "data_iscrizione", column = "data_iscrizione"),
			@Result(property = "isAdmin", column = "isAdmin"),
			@Result(property = "indirizzoResidenza.id_indirizzo", column = "id_indirizzo", id = true),
			@Result(property = "indirizzoResidenza.via", column = "via"),
			@Result(property = "indirizzoResidenza.n_civico", column = "n_civico"),
			@Result(property = "indirizzoResidenza.cap", column = "cap"),
			@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.id_province", column = "id_province", id = true),
			@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.nome_province", column = "nome_province"),
			@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.sigla_province", column = "sigla_province"),
			@Result(property = "indirizzoResidenza.provinciaDiAppartenenza.regione_province", column = "regione_province"),
		})
	@Select ( SELECT_ALL_USERS_JOIN_IND_PROV )
	@ResultType(Utente.class)
	List<Utente> selectAllUtenti();
	
	@Update ( ASSEGNA_CARRELLO )
	void assegnaNuovoCarrello( Carrello carrelloUtente, Utente loggedUser );

}
