package it.exolab.dao;

import java.util.HashMap;
import java.util.List;

import it.exolab.dto.Articolo;
import it.exolab.dto.Ordine;
import it.exolab.mybatis.SqlMapFactory;
import it.exolab.pojo.DettagliOrdinePOJO;

public class DettagliOrdineDAO {
	
	private static DettagliOrdineDAO instance;
	
	private DettagliOrdineDAO() {}
	
	public static DettagliOrdineDAO getInstance() {
		if(instance == null) {
			instance = new DettagliOrdineDAO();
		}
		return instance;
	}

	public List<DettagliOrdinePOJO> findAllDettagliOrdini() {
		MainDAO.beginTransaction();
		List<DettagliOrdinePOJO> extrapolatedDettagliOrdini = MainDAO.getDettagliOrdineMapper().findAllDettagliOrdini();
		MainDAO.closeTransaction();
		return extrapolatedDettagliOrdini;
	}
	
	public void insertDettagliOrdine(Ordine ordine, HashMap<Articolo, Integer> articoliEQuantita) {
		MainDAO.beginTransaction();
		MainDAO.getDettagliOrdineMapper().insertDettagliOrdine(ordine, articoliEQuantita);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}



}
