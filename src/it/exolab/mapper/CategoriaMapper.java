package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Categoria;

public interface CategoriaMapper {

	static String SELECT_ALL_CATEGORIE = "SELECT  "  
			+ " id_categoria, "  
			+ " nome_categoria, "  
			+ " descrizione_categoria "  
			+ "FROM "  
			+ " categorie";

	@Select ( SELECT_ALL_CATEGORIE )
	@ResultType( Categoria.class )
	List<Categoria> findAllCategorie();

}
