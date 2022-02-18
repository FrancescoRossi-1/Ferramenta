package it.exolab.dao;

import java.util.List;

import it.exolab.dto.CartaDiCredito;
import it.exolab.mybatis.SqlMapFactory;
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
	
	public List<CartaDiCredito> findAllCarte() {
		MainDAO.beginTransaction();
		List<CartaDiCredito> allCarteDiCreditoUtente = MainDAO.getCartaDiCreditoMapper().findAllCarte();
		MainDAO.closeTransaction();
		return allCarteDiCreditoUtente;
	}
	
	public List<CartaDiCredito> findAllByUserId( UtentePOJO utente ) {
		MainDAO.beginTransaction();
		List<CartaDiCredito> allCarteDiCreditoUtente = MainDAO.getCartaDiCreditoMapper().findAllByUserId(utente);
		MainDAO.closeTransaction();
		return allCarteDiCreditoUtente;
	}

	public void insertCartaDiCredito(CartaDiCredito cartaDiCredito) {
		MainDAO.beginTransaction();
		MainDAO.getCartaDiCreditoMapper().insertCartaDiCredito(cartaDiCredito);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();

	}

	public void deleteCartaDiCreditoFromId(CartaDiCredito cartaDiCredito) {
		MainDAO.beginTransaction();
		MainDAO.getCartaDiCreditoMapper().deleteCartaDiCreditoFromId(cartaDiCredito);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
