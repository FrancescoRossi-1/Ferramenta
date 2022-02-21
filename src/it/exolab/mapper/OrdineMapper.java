package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Ordine;
import it.exolab.pojo.OrdinePOJO;

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
			+ " id_carta_credito, "
			+ " data_consegna " 
			+ " ) " 
			+ "VALUES " 
			+ " ( " 
			+ " #{id_ordine}, " 
			+ " #{id_utente}, " 
			+ " #{totale_ordine}, " 
			+ " #{data_ordine} , " 
			+ " #{stato}, " 
			+ " #{id_indirizzo_spedizione}, " 
			+ " #{id_carta_credito}, "
			+ " #{data_consegna} " 
			+ " ) ";
	
	static final String FIND_ALL_ORDINI = " SELECT "
			+ " o.id_ordine,  " 
			+ " o.totale_ordine,  " 
			+ " o.data_ordine,  " 
			+ " o.stato,  " 
			+ " o.data_consegna, " 
			+ " u.id_utente, " 
			+ " u.nome, " 
			+ " u.cognome, " 
			+ " u.codice_fiscale, " 
			+ " u.data_nascita, " 
			+ " u.email, " 
			+ " u.password, " 
			+ " u.data_iscrizione, " 
			+ " u.isAdmin, " 
			+ " indres.id_indirizzo id_indirizzo_residenza, " 
			+ " indres.via via_indirizzo_residenza, " 
			+ " indres.n_civico n_civico_indirizzo_residenza, " 
			+ " indres.cap cap_indirizzo_residenza, " 
			+ " provres.id_province id_province_residenza, " 
			+ " provres.nome_province nome_province_residenza, " 
			+ " provres.sigla_province sigla_province_residenza, " 
			+ " provres.regione_province regione_province_residenza,  " 
			+ " ids.id_indirizzo_spedizione,  " 
			+ " ids.scala,  " 
			+ " ids.interno,  " 
			+ " ids.id_utente,  " 
			+ " indsped.id_indirizzo id_indirizzo_spedizione,  " 
			+ " indsped.via via_indirizzo_spedizione,  " 
			+ " indsped.n_civico n_civico_indirizzo_spedizione,  " 
			+ " indsped.cap cap_indirizzo_spedizione,  " 
			+ " provsped.id_province id_province_spedizione,  " 
			+ " provsped.nome_province nome_province_spedizione,  " 
			+ " provsped.regione_province regione_province_spedizione,  " 
			+ " provsped.sigla_province sigla_province_spedizione,  " 
			+ " cc.id_carte_di_credito,  " 
			+ " cc.numero_carta,  " 
			+ " cc.data_scadenza,"
			+ " cc.nome_circuito,  " 
			+ " cc.cvv,  " 
			+ " cc.nominativo_proprietario,  " 
			+ " cc.id_indirizzo_fatturazione,  " 
			+ " cc.id_utente,  " 
			+ " cc.nome_circuito,  " 
			+ " indfatt.id_indirizzo id_indirizzo_fatturazione,  " 
			+ " indfatt.via via_indirizzo_fatturazione,  " 
			+ " indfatt.n_civico n_civico_indirizzo_fatturazione,  " 
			+ " indfatt.cap cap_indirizzo_fatturazione,  "  
			+ " pfatt.id_province id_province_fatturazione,  " 
			+ " pfatt.nome_province nome_province_fatturazione,  " 
			+ " pfatt.regione_province regione_province_fatturazione,  " 
			+ " pfatt.sigla_province sigla_province_fatturazione " 
			+ "FROM  " 
			+ " ORDINE o " 
			+ "JOIN  " 
			+ " UTENTE u " 
			+ "ON " 
			+ " u.id_utente = o.id_utente " 
			+ "JOIN " 
			+ " INDIRIZZI indres " 
			+ "ON " 
			+ " indres.id_indirizzo = u.id_indirizzo " 
			+ "JOIN " 
			+ " PROVINCE provres " 
			+ "ON " 
			+ " indres.id_provincia = provres.id_province " 
			+ "JOIN  " 
			+ " INDIRIZZI_DI_SPEDIZIONE ids  " 
			+ "ON   " 
			+ " o.id_indirizzo_spedizione = ids.id_indirizzo_spedizione  " 
			+ "JOIN  " 
			+ " INDIRIZZI indsped  " 
			+ "ON  " 
			+ " ids.id_indirizzo = indsped.id_indirizzo  " 
			+ "JOIN  " 
			+ " PROVINCE provsped  " 
			+ "ON  " 
			+ " indsped.id_provincia = provsped.id_province  " 
			+ "JOIN  " 
			+ " CARTE_DI_CREDITO cc  " 
			+ "ON   " 
			+ " o.id_carta_credito = cc.id_carte_di_credito  " 
			+ "JOIN  " 
			+ " INDIRIZZI indfatt  " 
			+ "ON  " 
			+ " indfatt.id_indirizzo = cc.id_indirizzo_fatturazione  " 
			+ "JOIN  " 
			+ " PROVINCE pfatt  " 
			+ "ON  " 
			+ " indfatt.id_provincia = pfatt.id_province ";
	
	@Insert( INSERT_ORDINE )
	@Options(useGeneratedKeys = true, keyProperty = "id_ordine", keyColumn = "id_ordine")
	void insertOrdine(Ordine ordine);

	@Results ({
		@Result(property = "id", column = "id_ordine", id = true),
		@Result(property = "totaleOrdine", column = "totale_ordine"),
		@Result(property = "dataOrdine", column = "data_ordine"),
		@Result(property = "stato", column = "stato"),
		@Result(property = "dataConsegna", column = "data_consegna"),
		@Result(property = "acquirente.id", column = "id_utente",  id = true),
		@Result(property = "acquirente.nome", column = "nome"),
		@Result(property = "acquirente.cognome", column = "cognome"),
		@Result(property = "acquirente.codiceFiscale", column = "codice_fiscale"),
		@Result(property = "acquirente.dataNascita", column = "data_nascita"),
		@Result(property = "acquirente.email", column = "email"),
		@Result(property = "acquirente.password", column = "password"),
		@Result(property = "acquirente.dataIscrizione", column = "data_iscrizione"),
		@Result(property = "acquirente.isAdmin", column = "isAdmin"),
		@Result(property = "acquirente.indirizzoResidenza.idIndirizzo", column = "id_indirizzo_residenza", id = true),
		@Result(property = "acquirente.indirizzoResidenza.via", column = "via_indirizzo_residenza"),
		@Result(property = "acquirente.indirizzoResidenza.nCivico", column = "n_civico_indirizzo_residenza"),
		@Result(property = "acquirente.indirizzoResidenza.cap", column = "cap_indirizzo_residenza"),
		@Result(property = "acquirente.indirizzoResidenza.provinciaAppartenente.idProvincia", column = "id_province_residenza", id = true),
		@Result(property = "acquirente.indirizzoResidenza.provinciaAppartenente.nome", column = "nome_province_residenza"),
		@Result(property = "acquirente.indirizzoResidenza.provinciaAppartenente.sigla", column = "sigla_province_residenza"),
		@Result(property = "acquirente.indirizzoResidenza.provinciaAppartenente.regione", column = "regione_province_residenza"),
		@Result(property = "indirizzoDiSpedizione.id", column = "id_indirizzo_spedizione", id = true),
		@Result(property = "indirizzoDiSpedizione.scala", column = "scala"),
		@Result(property = "indirizzoDiSpedizione.interno", column = "interno"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.idIndirizzo", column = "id_indirizzo_spedizione", id = true),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.via", column = "via_indirizzo_spedizione"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.nCivico", column = "n_civico_indirizzo_spedizione"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.cap", column = "cap_indirizzo_spedizione"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.provinciaAppartenente.idProvincia", column = "id_province_spedizione", id = true),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.provinciaAppartenente.nome", column = "nome_province_spedizione"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.provinciaAppartenente.sigla", column = "sigla_province_spedizione"),
		@Result(property = "indirizzoDiSpedizione.indirizzoDiRiferimento.provinciaAppartenente.regione", column = "regione_province_spedizione"),
		@Result(property = "cartaDiCredito.id", column = "id_carte_di_credito" , id = true),
		@Result(property = "cartaDiCredito.numeroCarta", column = "numero_carta" ),
		@Result(property = "cartaDiCredito.dataScadenza", column = "data_scadenza" ),
		@Result(property = "cartaDiCredito.nomeCircuito", column = "nome_circuito" ),
		@Result(property = "cartaDiCredito.CVV", column = "CVV" ),
		@Result(property = "cartaDiCredito.nominativoProprietario", column = "nominativo_proprietario" ),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.idIndirizzo", column = "id_indirizzo_fatturazione", id = true ),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.via", column = "via_indirizzo_fatturazione"),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.nCivico", column = "n_civico_indirizzo_fatturazione"),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.cap", column = "cap_indirizzo_fatturazione"),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.provinciaAppartenente.idProvincia", column = "id_province_fatturazione", id = true),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.provinciaAppartenente.nome", column = "nome_province_fatturazione"),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.provinciaAppartenente.sigla", column = "sigla_province_fatturazione"),
		@Result(property = "cartaDiCredito.indirizzoFatturazione.provinciaAppartenente.regione", column = "regione_province_fatturazione"),
	})
	@Select ( FIND_ALL_ORDINI )
	@ResultType ( OrdinePOJO.class )
	List<OrdinePOJO> findAllOrdini();

}
