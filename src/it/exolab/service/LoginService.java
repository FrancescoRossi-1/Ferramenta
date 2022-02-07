package it.exolab.service;

import org.apache.log4j.Logger;

import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.UtenteNonEsistente;

public class LoginService {
	
	static Logger log = Logger.getLogger(LoginService.class);


	public static void checkParameters(Utente loginUser) throws CampoRichiesto {
		
		if(loginUser.getEmail().equals("")) {
			throw new CampoRichiesto("email");
		}
		
		if(loginUser.getPassword().equals("")) {
			throw new CampoRichiesto("password");
		}
		
	}
	
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
