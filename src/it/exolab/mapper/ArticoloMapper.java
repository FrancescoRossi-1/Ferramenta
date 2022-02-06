package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Articolo;

public interface ArticoloMapper {

	static String SELECT_ALL_ARTICOLI = " SELECT " 
			+ " a.id_articolo, "  
			+ " a.titolo_articolo, "  
			+ " a.descrizione_articolo, "
			+ " a.marchio, "
			+ " a.colore, "  
			+ " a.indirizzo_immagine, "  
			+ " a.quantita_disponibile, "
			+ " a.prezzo_unitario, "  
			+ " c.id_categoria, "  
			+ " c.nome_categoria, "  
			+ " c.descrizione_categoria "  
			+ "FROM  "  
			+ " articoli a "  
			+ "JOIN " 
			+ " categorie c " 
			+ "ON " 
			+ " a.id_categoria = c.id_categoria;";
	
	@Results({
		@Result(property = "id_articolo", column = "id_articolo", id = true),
		@Result(property = "titolo_articolo", column = "titolo_articolo"),
		@Result(property = "descrizione_articolo", column = "descrizione_articolo"),
		@Result(property = "marchio", column = "marchio"),
		@Result(property = "colore", column = "colore"),
		@Result(property = "indirizzo_immagine", column = "indirizzo_immagine"),
		@Result(property = "quantita_disponibile", column = "quantita_disponibile"),
		@Result(property = "prezzo_unitario", column = "prezzo_unitario"),
		@Result(property = "categoriaDiAppartenenza.id_categoria", column = "id_categoria", id = true),
		@Result(property = "categoriaDiAppartenenza.nome_categoria", column = "nome_categoria"),
		@Result(property = "categoriaDiAppartenenza.descrizione_categoria", column = "descrizione_categoria"),
	})
	@Select( SELECT_ALL_ARTICOLI )
	@ResultType( Articolo.class )
	List<Articolo> selectAllArticolo();

}
