package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Categoria;

public class CategoriaDAO {
	
	private static CategoriaDAO instance;
	
	private CategoriaDAO() {}
	
	public static CategoriaDAO getInstance() {
		if(instance == null) {
			instance = new CategoriaDAO();
		}
		return instance;
	}

	public List<Categoria> selectAllCategorie() {
		MainDAO.beginTransaction(); 
		List<Categoria> allCategorie = MainDAO.getCategoriaMapper().findAllCategorie();
		MainDAO.closeTransaction();
		return allCategorie;
	}

}
