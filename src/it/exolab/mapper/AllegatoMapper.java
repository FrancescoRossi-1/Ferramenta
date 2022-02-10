package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;

public interface AllegatoMapper {
	

	
	
	static String INSERT_ALL_ALLEGATI = "<script>" 
			+ "INSERT " 
			+ "		INTO ALLEGATI"
			+ "			(nome_file, " 
			+ "			estensione," 
			+ "			content, " 
			+ "			id_articolo)" 
			+ "		values " 
			+ " <foreach collection=\"list\" item=\"allegato\" separator=\" , \"> " 
			+ "			(#{allegato.nome_file}, "
			+ "			#{allegato.estensione}," 
			+ "			#{allegato.content}, " 
			+ "			#{allegato.id_articolo}) " 
			+ "	</foreach> " 
			+ "</script>";
	
	static String SELECT_ALL = " SELECT " 
			+ " id_allegato, " 
			+ " nome_file, " 
			+ " estensione, " 
			+ " content, " 
			+ " id_articolo " 
			+ "FROM " 
			+ " ALLEGATI ";

	@Insert ( INSERT_ALL_ALLEGATI )
	public void insertAllAllegati(List<Allegato> allegati);

	@Select ( SELECT_ALL )
	@ResultType ( Allegato.class )
	public List<Allegato> selectAllAllegati();

}
