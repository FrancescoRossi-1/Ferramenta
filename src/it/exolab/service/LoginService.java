package it.exolab.service;

import org.apache.log4j.Logger;

import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;
import it.exolab.exception.UtenteNonEsistente;

public class LoginService {
	
	static Logger log = Logger.getLogger(LoginService.class);

	public static void checkUser(Utente loginUser) throws UtenteNonEsistente {
		Utente extrapolatedUser = UtenteDAO.selectUserFromEmailAndPassword(loginUser);
		
		if(extrapolatedUser == null) {
			throw new UtenteNonEsistente();
		}
		
	}

	public static Utente selectUser(Utente loginUser) {
		return UtenteDAO.selectUser(loginUser);
	}
	
	

}
