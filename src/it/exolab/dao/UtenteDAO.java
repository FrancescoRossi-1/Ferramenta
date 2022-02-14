package it.exolab.dao;

import java.util.List;

import it.exolab.dto.Carrello;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.Utente;
import it.exolab.mybatis.SqlMapFactory;
import it.exolab.pojo.UtentePOJO;

public class UtenteDAO {

	private static UtenteDAO instance = null;

	private UtenteDAO() {}

	public static UtenteDAO getInstance() {
		if(instance == null) {
			instance = new UtenteDAO();
		}
		return instance;
	}


	public void insertUser(Utente user,Indirizzo indirizzo) {
		MainDAO.beginTransaction();
		MainDAO.getUtenteMapper().insertUser(user,indirizzo);
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

	public Utente selectUserFromEmailAndPassword(Utente user) {
		MainDAO.beginTransaction();
		Utente extrapolatedUser = MainDAO.getUtenteMapper().selectUserFromEmailAndPassword(user);
		MainDAO.closeTransaction();
		return extrapolatedUser;
	}

	public UtentePOJO selectUser(Utente user) {
		MainDAO.beginTransaction();
		UtentePOJO extrapolatedUser = MainDAO.getUtenteMapper().selectUser(user);
		MainDAO.closeTransaction();
		return extrapolatedUser;
	}

	public List<UtentePOJO> selectAllUtenti() {
		MainDAO.beginTransaction();
		List<UtentePOJO> allUtenti = MainDAO.getUtenteMapper().selectAllUtenti();
		MainDAO.closeTransaction();
		return allUtenti;
	}

	public void assegnaNuovoCarrello(Carrello carrelloUtente, UtentePOJO loggedUser) {
		MainDAO.beginTransaction();
		MainDAO.getUtenteMapper().assegnaNuovoCarrello( carrelloUtente, loggedUser );
		SqlMapFactory.instance().commitSession();
		MainDAO.closeTransaction();
	}

}
