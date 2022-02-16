package it.exolab.dao;

import it.exolab.dto.Carrello;
import it.exolab.mybatis.SqlMapFactory;
import it.exolab.pojo.UtentePOJO;

public class CarrelloDAO {
	
	private static CarrelloDAO instance;
	
	private CarrelloDAO() {	}
	
	public static CarrelloDAO getInstance() {
		if( instance == null ) {
			instance = new CarrelloDAO();
		}
		return instance;
	}
	
	public Carrello findCarrelloByUserId(UtentePOJO utente) {
	
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

	public void deleteFromUserId(Carrello carrello) {
		MainDAO.beginTransaction();
		MainDAO.getCarrelloMapper().deleteFromUserId(carrello);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
