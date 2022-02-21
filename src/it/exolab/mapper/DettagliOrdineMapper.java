package it.exolab.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Articolo;
import it.exolab.dto.Ordine;
import it.exolab.pojo.DettagliOrdinePOJO;

public interface DettagliOrdineMapper {
	
	static final String FIND_ALL_DETTAGLI_ORDINI = " SELECT " 
			+ " dettord.id_dettagli_ordine, " 
			+ " dettord.quantita, " 
			+ " art.id_articolo, " 
			+ " art.titolo_articolo, " 
			+ " art.descrizione_articolo, " 
			+ " art.quantita_disponibile, " 
			+ " art.prezzo_unitario, " 
			+ " art.marchio, " 
			+ " art.colore, " 
			+ " categ.id_categoria, " 
			+ " categ.nome_categoria, " 
			+ " categ.nome_categoria, "
			+ " ordi.id_ordine " 
			+ "FROM  " 
			+ " DETTAGLI_ORDINE dettord " 
			+ "JOIN " 
			+ " ORDINE ordi " 
			+ "ON " 
			+ " dettord.id_ordine = ordi.id_ordine " 
			+ "JOIN " 
			+ " ARTICOLI art " 
			+ "ON " 
			+ " art.id_articolo = dettord.id_articolo " 
			+ "JOIN " 
			+ " CATEGORIE categ " 
			+ "ON " 
			+ " categ.id_categoria = art.id_categoria ";
	
	static final String INSERT_DETTAGLI_ORDINE = "<script>"
			+ "INSERT  " 
			+ "INTO " 
			+ " DETTAGLI_ORDINE " 
			+ " (id_ordine, " 
			+ " id_articolo, " 
			+ " quantita) " 
			+ "VALUES " 
			+ "<foreach collection=\"entries.entrySet()\" index=\"index\" item=\"item\" separator=\" , \">"
			+ " ( " 
			+ " #{param1.id_ordine}, " 
			+ " #{index.id_articolo}, " 
			+ " #{item.value} " 
			+ " ) "
			+ "	</foreach> "
			+ "</script>" ;
	
	static final String FIND_FROM_ID_ORDINE = 
			FIND_ALL_DETTAGLI_ORDINI
			+ "WHERE "
			+ " ordi.id_ordine = #{id_ordine}";
	

	@Results({
		@Result(property = "id", column = "id_dettagli_ordine", id = true),
		@Result(property = "quantitaArticolo", column = "quantita"),
		@Result(property = "articoloAcquistato.id", column = "id_articolo", id = true),
		@Result(property = "articoloAcquistato.titolo", column = "titolo_articolo"),
		@Result(property = "articoloAcquistato.descrizione", column = "descrizione_articolo"),
		@Result(property = "articoloAcquistato.marchio", column = "marchio"),
		@Result(property = "articoloAcquistato.colore", column = "colore"),
		@Result(property = "articoloAcquistato.quantitaDisponibile", column = "quantita_disponibile"),
		@Result(property = "articoloAcquistato.prezzoUnitario", column = "prezzo_unitario"),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.id_categoria", column = "id_categoria", id = true),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.nome_categoria", column = "nome_categoria"),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.descrizione_categoria", column = "descrizione_categoria"),
		@Result(property = "ordineDiRiferimento.id_ordine", column = "id_ordine" ),
	})
	@Select ( FIND_ALL_DETTAGLI_ORDINI )
	List<DettagliOrdinePOJO> findAllDettagliOrdini();
	
	@Insert( INSERT_DETTAGLI_ORDINE )
	void insertDettagliOrdine(Ordine ordine, @Param("entries") HashMap<Articolo, Integer> articoliEQuantita);

	@Results({
		@Result(property = "id", column = "id_dettagli_ordine", id = true),
		@Result(property = "quantitaArticolo", column = "quantita"),
		@Result(property = "articoloAcquistato.id", column = "id_articolo", id = true),
		@Result(property = "articoloAcquistato.titolo", column = "titolo_articolo"),
		@Result(property = "articoloAcquistato.descrizione", column = "descrizione_articolo"),
		@Result(property = "articoloAcquistato.marchio", column = "marchio"),
		@Result(property = "articoloAcquistato.colore", column = "colore"),
		@Result(property = "articoloAcquistato.quantitaDisponibile", column = "quantita_disponibile"),
		@Result(property = "articoloAcquistato.prezzoUnitario", column = "prezzo_unitario"),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.id_categoria", column = "id_categoria", id = true),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.nome_categoria", column = "nome_categoria"),
		@Result(property = "articoloAcquistato.categoriaDiAppartenenza.descrizione_categoria", column = "descrizione_categoria"),
		@Result(property = "ordineDiRiferimento.id_ordine", column = "id_ordine" ),
	})
	@Select ( FIND_FROM_ID_ORDINE )
	List<DettagliOrdinePOJO> findFromIdOrdine(Ordine ordine);

	

}
