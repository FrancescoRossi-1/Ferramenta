package it.exolab.dao;

import it.exolab.dto.Indirizzo;
import it.exolab.mybatis.SqlMapFactory;

public class IndirizzoDAO {
	
	private static IndirizzoDAO instance = null;
	
	private IndirizzoDAO() {
		
	}
	
	public static IndirizzoDAO getInstance() {
		if(instance == null) {
			instance = new IndirizzoDAO();
		}
		return instance;
	}

	public Indirizzo selectIndirizzoForEquals(Indirizzo indirizzoResidenza) {
		MainDAO.beginTransaction(); 
		Indirizzo indirizzo = MainDAO.getIndirizzoMapper().selectIndirizzoFromFields(indirizzoResidenza);
		MainDAO.closeTransaction();
		return indirizzo;
	}
	
	public void insertAddress(Indirizzo indirizzoResidenza) {
		MainDAO.beginTransaction();
		MainDAO.getIndirizzoMapper().insertAddress(indirizzoResidenza);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}



}
