package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Provincia;

public class ProvinciaDAO {

	public static ProvinciaDAO instance = null;

	private ProvinciaDAO() { }

	public static ProvinciaDAO getInstance() {
		if( ProvinciaDAO.instance == null ) {
			ProvinciaDAO.instance = new ProvinciaDAO();
		}
		return ProvinciaDAO.instance;
	}

	public static List<Provincia> findAll() {
		MainDAO.beginTransaction();
		List<Provincia> allProvince = MainDAO.getProvinciaMapper().findAll();
		MainDAO.closeTransaction();
		return allProvince;
	}

}
