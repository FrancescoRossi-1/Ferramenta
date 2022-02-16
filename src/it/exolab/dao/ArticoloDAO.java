package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Articolo;
import it.exolab.mybatis.SqlMapFactory;

public class ArticoloDAO {
	
	private static ArticoloDAO instance;
	
	private ArticoloDAO () {
		
	}
	
	public static ArticoloDAO getInstance() {
		if( instance == null ) {
			instance = new ArticoloDAO();
		}
		return instance;
	}

	public List<Articolo> selectAllArticoli() {
		MainDAO.beginTransaction();
		List<Articolo> allArticoli = MainDAO.getArticoloMapper().selectAllArticolo();
		MainDAO.closeTransaction();
		return allArticoli;
	}

	public void insertArticolo(Articolo addArticolo) {
		MainDAO.beginTransaction();
		MainDAO.getArticoloMapper().insertArticolo(addArticolo);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public void deleteArticoloFromId(Long idArticolo) {
		MainDAO.beginTransaction();
		MainDAO.getArticoloMapper().deleteArticoloFromId(idArticolo);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public void updateQuantitaDisponibile(List<Articolo> articoli) {
		MainDAO.beginTransaction();
		MainDAO.getArticoloMapper().updateQuantitaDisponibile(articoli);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
