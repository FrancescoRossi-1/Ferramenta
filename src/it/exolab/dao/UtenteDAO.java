package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Utente;
import it.exolab.mybatis.SqlMapFactory;

public class UtenteDAO {

	private static UtenteDAO instance = null;

	private UtenteDAO() {}

	public static UtenteDAO getInstance() {
		if(instance == null) {
			instance = new UtenteDAO();
		}
		return instance;
	}

	public Utente selectUserFromEmail(Utente user) {
		MainDAO.beginTransaction();
		Utente extrapolatedUser = MainDAO.getUtenteMapper().selectUserFromEmail(user);
		MainDAO.closeTransaction();
		return extrapolatedUser;
	}

	public void insertUser(Utente user) {
		MainDAO.beginTransaction();
		MainDAO.getUtenteMapper().insertUser(user);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public Utente selectUserFromEmailAndPassword(Utente user) {
		MainDAO.beginTransaction();
		Utente extrapolatedUser = MainDAO.getUtenteMapper().selectUserFromEmailAndPassword(user);
		MainDAO.closeTransaction();
		return extrapolatedUser;
	}

	public Utente selectUser(Utente user) {
		MainDAO.beginTransaction();
		Utente extrapolatedUser = MainDAO.getUtenteMapper().selectUser(user);
		MainDAO.closeTransaction();
		return extrapolatedUser;
	}

	public List<Utente> selectAllUtenti() {
		MainDAO.beginTransaction();
		List<Utente> allUtenti = MainDAO.getUtenteMapper().selectAllUtenti();
		MainDAO.closeTransaction();
		return allUtenti;
	}

}
