package it.exolab.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.exolab.dto.Articolo;
import it.exolab.dto.Ordine;
import it.exolab.mybatis.SqlMapFactory;

public class DettagliOrdineDAO {
	
	private static DettagliOrdineDAO instance;
	
	private DettagliOrdineDAO() {}
	
	public static DettagliOrdineDAO getInstance() {
		if(instance == null) {
			instance = new DettagliOrdineDAO();
		}
		return instance;
	}

	public void insertDettagliOrdine(Ordine ordine, HashMap<Articolo, Integer> articoliEQuantita) {
		MainDAO.beginTransaction();
		MainDAO.getDettagliOrdineMapper().insertDettagliOrdine(ordine, articoliEQuantita);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
