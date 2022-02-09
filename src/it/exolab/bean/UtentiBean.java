package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.Pool;

import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class UtentiBean implements Serializable {
	
	private static final long serialVersionUID = -681643631408265088L;

	static Logger log = Logger.getLogger(UtentiBean.class);
	
	List<Utente> allUtenti;
	Utente utenteSelezionato;
	
	@PostConstruct
	public void init() {
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();
		utenteSelezionato = new Utente();
	}

	public List<Utente> getAllUtenti() {
		return allUtenti;
	}

	public void setAllUtenti(List<Utente> allUtenti) {
		this.allUtenti = allUtenti;
	}

	public Utente getUtenteSelezionato() {
		return utenteSelezionato;
	}

	public void setUtenteSelezionato(Utente utenteSelezionato) {
		this.utenteSelezionato = utenteSelezionato;
		log.info("Utente settato -> " + utenteSelezionato.toString());
	}

}
