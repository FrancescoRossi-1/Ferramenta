package it.exolab.dao;

import java.util.List;

import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.mybatis.SqlMapFactory;
import it.exolab.pojo.UtentePOJO;

public class IndirizzoDiSpedizioneDAO {

	private static IndirizzoDiSpedizioneDAO instance;

	private IndirizzoDiSpedizioneDAO() { }

	public static IndirizzoDiSpedizioneDAO getInstance() {
		if( instance == null ) {
			instance = new IndirizzoDiSpedizioneDAO();
		}
		return instance;
	}

	public List<IndirizzoDiSpedizione> findAllByUserId(UtentePOJO utente) {
		MainDAO.beginTransaction();
		List<IndirizzoDiSpedizione> allIndirizziDiSpedizioneUtente = MainDAO.getIndirizzoDiSpedizioneMapper().findAllByUserId(utente);
		MainDAO.closeTransaction();
		return allIndirizziDiSpedizioneUtente;
	}

	public List<IndirizzoDiSpedizione> findAll() {
		MainDAO.beginTransaction();
		List<IndirizzoDiSpedizione> allIndirizziDiSpedizioneUtente = MainDAO.getIndirizzoDiSpedizioneMapper().findAll();
		MainDAO.closeTransaction();
		return allIndirizziDiSpedizioneUtente;
	}

	public void insertIndirizzoDiSpedizione(IndirizzoDiSpedizione indirizzoSpedizione, UtentePOJO utente) {
		MainDAO.beginTransaction();
		MainDAO.getIndirizzoDiSpedizioneMapper().insertIndirizzoSpedizione(indirizzoSpedizione, utente);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public void deleteIndirizzoDiSpedizioneFromId(IndirizzoDiSpedizione indirizzoDiSpedizione) {
		MainDAO.beginTransaction();
		MainDAO.getIndirizzoDiSpedizioneMapper().deleteIndirizzoDiSpedizioneFromId(indirizzoDiSpedizione);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
