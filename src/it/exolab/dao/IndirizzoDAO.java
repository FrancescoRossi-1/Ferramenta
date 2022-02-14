package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Indirizzo;
import it.exolab.dto.Provincia;
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

	
	public void insertAddress(Indirizzo indirizzoResidenza, Long idProvincia) {
		MainDAO.beginTransaction();
		MainDAO.getIndirizzoMapper().insertAddress(indirizzoResidenza, idProvincia);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public List<Indirizzo> findAll() {
		MainDAO.beginTransaction(); 
		List<Indirizzo> allIndirizzi = MainDAO.getIndirizzoMapper().findAllIndirizzi();
		MainDAO.closeTransaction();
		return allIndirizzi;
	}



}
