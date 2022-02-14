package it.exolab.dao;

import java.util.List;
import org.apache.log4j.Logger;

import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import it.exolab.dto.CarrelloEArticolo;
import it.exolab.mybatis.SqlMapFactory;

public class CarrelloEArticoloDAO {
	
	static Logger log = Logger.getLogger(CarrelloEArticolo.class);
 
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

	public void deleteRigaFromIdCarrelloEIdArticolo(Carrello carrello, Articolo articolo) {
		MainDAO.beginTransaction();
		MainDAO.getCarrelloEArticoloMapper().deleteRigaFromIds(carrello, articolo);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}
	
	
}
