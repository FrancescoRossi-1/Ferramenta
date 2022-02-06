package it.exolab.mapper;

import it.exolab.dto.Indirizzo;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface IndirizzoMapper extends ProvinciaMapper {

	static String SELECT_INDIRIZZO_FROM_FIELDS = "SELECT " 
			+ " i.id_indirizzo, "  
			+ " i.via, " 
			+ " i.n_civico, " 
			+ " i.cap, " 
			+ " i.id_provincia,"  
			+ " p.nome_province, "  
			+ " p.sigla_province, "  
			+ " p.regione_province "  
			+ "FROM " 
			+ " indirizzi i "  
			+ " JOIN province p "  
			+ " ON i.id_provincia = p.id_province "  
			+ "WHERE " 
			+ " via = #{via} "  
			+ "AND " 
			+ " n_civico = #{n_civico} "  
			+ "AND " 
			+ " cap = #{cap} "  
			+ "AND  " 
			+ " id_provincia = #{provinciaDiAppartenenza.id_province} ";

	static String INSERT_ADDRESS = " INSERT "  
			+ "INTO indirizzi "  
			+ " (via, " 
			+ " n_civico, "  
			+ " cap, " 
			+ " id_provincia) " 
			+ "VALUES " 
			+ " (#{via}, "  
			+ " #{n_civico},  " 
			+ " #{cap}, "  
			+ " #{provinciaDiAppartenenza.id_province} ) ";

	@Results( id = "indirizziProvince", value = {
			@Result(property = "id_indirizzo", column = "id_indirizzo", id = true),
			@Result(property = "via", column = "via"),
			@Result(property = "n_civico", column = "n_civico"),
			@Result(property = "cap", column = "cap"),
			@Result(property = "provinciaDiAppartenenza.id_province", column = "id_province", id = true),
			@Result(property = "provinciaDiAppartenenza.nome_province", column = "nome_province"),
			@Result(property = "provinciaDiAppartenenza.sigla_province", column = "sigla_province"),
			@Result(property = "provinciaDiAppartenenza.regione_province", column = "regione_province"),
	})
	@Select(SELECT_INDIRIZZO_FROM_FIELDS)
	@ResultType(Indirizzo.class)
	Indirizzo selectIndirizzoFromFields(Indirizzo indirizzoResidenza);

	@Insert(INSERT_ADDRESS)
	@Options(useGeneratedKeys = true, keyProperty = "id_indirizzo", keyColumn = "id_indirizzo")
	void insertAddress(Indirizzo indirizzoResidenza);

}
