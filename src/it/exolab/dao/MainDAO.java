package it.exolab.dao;

import it.exolab.mapper.AllegatoMapper;
import it.exolab.mapper.ArticoloMapper;
import it.exolab.mapper.CarrelloEArticoloMapper;
import it.exolab.mapper.CarrelloMapper;
import it.exolab.mapper.CartaDiCreditoMapper;
import it.exolab.mapper.CategoriaMapper;
import it.exolab.mapper.DettagliOrdineMapper;
import it.exolab.mapper.IndirizzoDiSpedizioneMapper;
import it.exolab.mapper.IndirizzoMapper;
import it.exolab.mapper.OrdineMapper;
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
	
	public static CategoriaMapper getCategoriaMapper() {
		return SqlMapFactory.instance().getMapper(CategoriaMapper.class);
	}
	
	public static AllegatoMapper getAllegatoMapper() {
		return SqlMapFactory.instance().getMapper(AllegatoMapper.class);
	}
	
	public static CarrelloMapper getCarrelloMapper() {
		return SqlMapFactory.instance().getMapper(CarrelloMapper.class);
	}
	
	public static CarrelloEArticoloMapper getCarrelloEArticoloMapper() {
		return SqlMapFactory.instance().getMapper(CarrelloEArticoloMapper.class);
	}
	

	public static CartaDiCreditoMapper getCartaDiCreditoMapper() {
		return SqlMapFactory.instance().getMapper(CartaDiCreditoMapper.class);

	}
	
	public static IndirizzoDiSpedizioneMapper getIndirizzoDiSpedizioneMapper() {
		return SqlMapFactory.instance().getMapper(IndirizzoDiSpedizioneMapper.class);

	}
	
	public static OrdineMapper getOrdineMapper() {
		return SqlMapFactory.instance().getMapper(OrdineMapper.class);
	}
	
	public static DettagliOrdineMapper getDettagliOrdineMapper() {
		return SqlMapFactory.instance().getMapper(DettagliOrdineMapper.class);
	}

	
	public static void closeTransaction() {
		SqlMapFactory.instance().closeSession();
	}

	public static void beginTransaction() {
		SqlMapFactory.instance().openSession();
	}




}
