package it.exolab.dao;

import it.exolab.mapper.ArticoloMapper;
import it.exolab.mapper.IndirizzoMapper;
import it.exolab.mapper.ProvinciaMapper;
import it.exolab.mapper.UtenteMapper;
import it.exolab.mybatis.SqlMapFactory;

public class MainDAO {
	
	public static ProvinciaMapper getProvinciaMapper() {
		return SqlMapFactory.instance().getMapper(ProvinciaMapper.class);
	}
	
	public static IndirizzoMapper getIndirizzoMapper() {
		return SqlMapFactory.instance().getMapper(IndirizzoMapper.class);
	}
	
	public static UtenteMapper getUtenteMapper() {
		return SqlMapFactory.instance().getMapper(UtenteMapper.class);
	}
	
	public static ArticoloMapper getArticoloMapper() {
		return SqlMapFactory.instance().getMapper(ArticoloMapper.class);
	}
	
	public static void closeTransaction() {
		SqlMapFactory.instance().closeSession();
	}

	public static void beginTransaction() {
		SqlMapFactory.instance().openSession();
	}




}
