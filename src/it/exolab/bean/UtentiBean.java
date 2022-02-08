package it.exolab.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class UtentiBean {
	
	static Logger log = Logger.getLogger(UtentiBean.class);
	
	List<Utente> allUtenti;
	
	public void init() {
		UtenteDAO.getInstance().selectAllUtenti();
	}
	
	public void refreshUtenti() {

		//estrapola tutti gli utenti
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();
		log.info("Utenti aggiornati.");
		
	}

	public List<Utente> getAllUtenti() {
		return allUtenti;
	}

	public void setAllUtenti(List<Utente> allUtenti) {
		this.allUtenti = allUtenti;
	}

}
