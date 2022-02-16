package it.exolab.dao;

import it.exolab.dto.Ordine;
import it.exolab.mybatis.SqlMapFactory;

public class OrdineDAO {
	
	private static OrdineDAO instance;

	private OrdineDAO() {}
	
	public static OrdineDAO getInstance() {
		if(instance == null) {
			instance = new OrdineDAO();
		}
		return instance;
	}

	public void insertOrdine(Ordine ordine) {
		MainDAO.beginTransaction();
		MainDAO.getOrdineMapper().insertOrdine(ordine);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}
	
}
