package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.exolab.dto.Articolo;
import it.exolab.dto.Ordine;

public interface ArticoloMapper {

	static final String SELECT_ALL_ARTICOLI = "SELECT " 
			+ " art.id_articolo, " 
			+ " art.titolo_articolo, " 
			+ " art.descrizione_articolo, " 
			+ " art.marchio," 
			+ " art.colore, " 
			+ " art.quantita_disponibile, " 
			+ " art.prezzo_unitario, " 
			+ " c.id_categoria ," 
			+ " c.nome_categoria, " 
			+ " c.nome_categoria, " 
			+ " c.descrizione_categoria " 
			+ "FROM" 
			+ " ARTICOLI art " 
			+ "JOIN" 
			+ " CATEGORIE c " 
			+ "ON " 
			+ " art.id_categoria = c.id_categoria" ;

	static final String INSERT_ARTICOLO = " INSERT " 
			+ "INTO " 
			+ " ARTICOLI "  
			+ " ( " 
			+ " titolo_articolo, "  
			+ " descrizione_articolo, "  
			+ " quantita_disponibile, "  
			+ " prezzo_unitario, " 
			+ " id_categoria, " 
			+ " marchio, " 
			+ " colore " 
			+ " ) " 
			+ "VALUES " 
			+ " ( " 
			+ " #{titolo_articolo}, "  
			+ " #{descrizione_articolo}, "  
			+ " #{quantita_disponibile}, "  
			+ " #{prezzo_unitario}, " 
			+ " #{categoriaDiAppartenenza.id_categoria}, "  
			+ " #{marchio}, " 
			+ " #{colore} " 
			+ " ) ";

	static final String DELETE_FROM_ID = " DELETE "  
			+ "FROM " 
			+ " ARTICOLI " 
			+ "WHERE " 
			+ " id_articolo = #{id_articolo} ";

	static final String UPDATE_QUANTITA_DISPONIBILE_ALL = "<script>"
			+ " UPDATE "
			+ " ARTICOLI a "
			+ "JOIN "
			+ " DETTAGLI_ORDINE dett "
			+ "ON  "
			+ " a.id_articolo = dett.id_articolo "
			+ "SET "
			+ " a.quantita_disponibile = (a.quantita_disponibile - dett.quantita) "
			+ "WHERE  "
			+ "<foreach collection=\"articoli\" item=\"articolo\" separator=\" OR \">"
			+ " a.id_articolo = #{articolo.id_articolo} "
			+ "</foreach>"
			+ "AND "
			+ " id_ordine = #{ordine.id_ordine}"
			+ "</script>";

	static final String UPDATE_QUANTITA_DISPONIBILE =  " UPDATE "
			+ " ARTICOLI a "
			+ "SET "
			+ " a.quantita_disponibile = (a.quantita_disponibile + #{quantita_disponibile}) "
			+ "WHERE  "
			+ " id_articolo = #{id_articolo} ";

	@Results( value = {
			@Result(property = "id_articolo", column = "id_articolo", id = true),
			@Result(property = "titolo_articolo", column = "titolo_articolo"),
			@Result(property = "descrizione_articolo", column = "descrizione_articolo"),
			@Result(property = "marchio", column = "marchio"),
			@Result(property = "colore", column = "colore"),
			@Result(property = "quantita_disponibile", column = "quantita_disponibile"),
			@Result(property = "prezzo_unitario", column = "prezzo_unitario"),
			@Result(property = "categoriaDiAppartenenza.id_categoria", column = "id_categoria", id = true),
			@Result(property = "categoriaDiAppartenenza.nome_categoria", column = "nome_categoria"),
			@Result(property = "categoriaDiAppartenenza.descrizione_categoria", column = "descrizione_categoria"),
	})
	@Select( SELECT_ALL_ARTICOLI )
	@ResultType( Articolo.class )
	List<Articolo> selectAllArticolo();

	@Insert( INSERT_ARTICOLO )
	@Options(useGeneratedKeys = true, keyProperty = "id_articolo", keyColumn = "id_articolo")
	void insertArticolo(Articolo articolo);

	@Delete ( DELETE_FROM_ID )
	void deleteArticoloFromId(Long idArticolo);

	@Update ( UPDATE_QUANTITA_DISPONIBILE_ALL )
	void updateQuantitaDisponibileAll(@Param("articoli") List<Articolo> articoli,@Param("ordine") Ordine ordine);

	@Update ( UPDATE_QUANTITA_DISPONIBILE )
	void updateQuantitaDisponibile(Articolo articolo);

}
