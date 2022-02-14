package it.exolab.dao;

import java.util.List;

import it.exolab.dto.CartaDiCredito;
import it.exolab.pojo.UtentePOJO;

public class CartaDiCreditoDAO {
	
	private static CartaDiCreditoDAO instance;
	
	private CartaDiCreditoDAO() { }
	
	public static CartaDiCreditoDAO getInstance() {
		if( instance == null ) {
			instance = new CartaDiCreditoDAO();
		}
		return instance;
	}
	
	public List<CartaDiCredito> findAllByUserId( UtentePOJO utente ) {
		
		MainDAO.beginTransaction();
		List<CartaDiCredito> allCarteDiCreditoUtente = MainDAO.getCartaDiCreditoMapper().findAllByUserId(utente);
		MainDAO.closeTransaction();
		return allCarteDiCreditoUtente;
	}

}
