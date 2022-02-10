package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;
import it.exolab.mybatis.SqlMapFactory;

public class AllegatoDAO {
	
	private static AllegatoDAO instance = null;
	
	private AllegatoDAO () {}
	
	public static AllegatoDAO getInstance() {
		if(instance == null) {
			instance = new AllegatoDAO();
		}
		return instance;
	}

	public static void insertAll(List<Allegato> allegati) {
		MainDAO.beginTransaction();
		MainDAO.getAllegatoMapper().insertAllAllegati(allegati);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}


	public List<Allegato> selectAllAllegati() {
		MainDAO.beginTransaction();
		List<Allegato> allAllegati = MainDAO.getAllegatoMapper().selectAllAllegati();
		MainDAO.closeTransaction();
		return allAllegati;
	}

}
