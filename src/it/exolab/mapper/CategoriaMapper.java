package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Categoria;

public interface CategoriaMapper {

	static String SELECT_ALL_CATEGORIE = " SELECT  "  
			+ " id_categoria, "  
			+ " nome_categoria, "  
			+ " descrizione_categoria "  
			+ "FROM "  
			+ " categorie";
	
	static String INSERT_CATEGORIA = " INSERT "
			+ "INTO "
			+ " CATEGORIE "
			+ " ("
			+ " nome_categoria, "
			+ " descrizione_categoria"
			+ " )"
			+ "VALUES "
			+ " ("
			+ " #{nome_categoria}, "
			+ " #{descrizione_categoria}"
			+ " ) ";
	
	static String DELETE_CATEGORIA = " DELETE "
			+ "FROM"
			+ " CATEGORIE "
			+ "WHERE "
			+ " id_categoria = #{id_categoria} ";

	@Select ( SELECT_ALL_CATEGORIE )
	@ResultType( Categoria.class )
	List<Categoria> findAllCategorie();

	@Insert( INSERT_CATEGORIA )
	void insertCategoria(Categoria categoria);

	@Delete ( DELETE_CATEGORIA )
	void deleteCategoria(Long idCategoria);

}
