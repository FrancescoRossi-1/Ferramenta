package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Provincia;

public interface ProvinciaMapper {

	static final String TUTTE_PROVINCE = " SELECT "
			+ " id_province , "
			+ " nome_province , "
			+ " sigla_province , "
			+ " regione_province "
			+ "FROM "
			+ " PROVINCE";

	@Select(TUTTE_PROVINCE)
	@ResultType(Provincia.class)
	public List<Provincia> findAll();

}
