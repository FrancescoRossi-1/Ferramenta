package it.exolab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import it.exolab.dto.Allegato;

public interface AllegatoMapper {
	
	static String SELECT_BY_ID_ARTICOLO = "SELECT " 
			+ " id_allegato, " 
			+ " nome_file, "  
			+ " estensione,"  
			+ " content, "  
			+ " id_articolo "  
			+ "FROM " 
			+ " ALLEGATI"  
			+ "WHERE " 
			+ " id_articolo = #{id_articolo} ";
	
	
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

	
	@Select ( SELECT_BY_ID_ARTICOLO )
	@ResultType ( Allegato.class )
	public List<Allegato> selectByIdArticolo();

	
	@Insert ( INSERT_ALL_ALLEGATI )
	public void insertAllAllegati(List<Allegato> allegati);

}
