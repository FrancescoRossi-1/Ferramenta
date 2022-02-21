package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Ordine;
import it.exolab.mybatis.SqlMapFactory;
import it.exolab.pojo.OrdinePOJO;

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

	public List<OrdinePOJO> findAllOrdini() {
		MainDAO.beginTransaction();
		List<OrdinePOJO> extrapolatedOrdini = MainDAO.getOrdineMapper().findAllOrdini();
		MainDAO.closeTransaction();
		return extrapolatedOrdini;
	}

	public OrdinePOJO findPOJOFromId(Ordine ordine) {
		MainDAO.beginTransaction();
		OrdinePOJO extrapolatedOrdine = MainDAO.getOrdineMapper().findPOJOFromId(ordine);
		MainDAO.closeTransaction();
		return extrapolatedOrdine;
	}
	
}
