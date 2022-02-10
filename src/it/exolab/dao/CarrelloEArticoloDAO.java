package it.exolab.dao;

import java.util.List;
import java.util.Map;

import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import it.exolab.dto.CarrelloEArticolo;
import it.exolab.mybatis.SqlMapFactory;

public class CarrelloEArticoloDAO {
 
	private static CarrelloEArticoloDAO instance = null;
	
	private CarrelloEArticoloDAO() { }
	
	public static CarrelloEArticoloDAO getInstance() {
		if( instance == null ) {
			instance = new CarrelloEArticoloDAO();
		}
		return instance;
	}

	public void insertRiga(Articolo articoloDaAggiungere, Integer quantita, Carrello carrelloUtente) {
		MainDAO.beginTransaction();
		MainDAO.getCarrelloEArticoloMapper().insertRiga(articoloDaAggiungere, quantita, carrelloUtente);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public List<CarrelloEArticolo> selectRigheFromIdCarrello(Carrello carrello) {
		MainDAO.beginTransaction();
		List<CarrelloEArticolo> extrapolatedRighe = MainDAO.getCarrelloEArticoloMapper().selectRigheFromIdCarrello(carrello);
		MainDAO.closeTransaction();
		return extrapolatedRighe;
	}
	
	
}
