package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Articolo;

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

}
