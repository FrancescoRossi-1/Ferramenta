package it.exolab.dao;

import java.util.Date;

import it.exolab.dto.Carrello;
import it.exolab.dto.Utente;
import it.exolab.mybatis.SqlMapFactory;

public class CarrelloDAO {
	
	private static CarrelloDAO instance;
	
	private CarrelloDAO() {	}
	
	public static CarrelloDAO getInstance() {
		if( instance == null ) {
			instance = new CarrelloDAO();
		}
		return instance;
	}
	
	public Carrello findCarrelloByUserId(Utente utente) {
	
		MainDAO.beginTransaction();
		Carrello extrapolatedCarrello = MainDAO.getCarrelloMapper().findByIdUtente(utente);
		MainDAO.closeTransaction();
		return extrapolatedCarrello;
		
	}

	public Carrello createNewCarrello(Carrello carrello) {
		
		MainDAO.beginTransaction();
		MainDAO.getCarrelloMapper().createNewCarrello(carrello);
		Carrello extrapolatedCarrello = MainDAO.getCarrelloMapper().findByIdCarello(carrello);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
		return extrapolatedCarrello;
	}


	public void updateTotaleEUltimaModifica(Carrello carrello) {
		MainDAO.beginTransaction();
		MainDAO.getCarrelloMapper().updateTotaleEUltimaModifica(carrello);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
