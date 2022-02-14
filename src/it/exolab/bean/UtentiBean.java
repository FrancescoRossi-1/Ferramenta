package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import it.exolab.dao.UtenteDAO;
import it.exolab.pojo.UtentePOJO;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class UtentiBean implements Serializable {
	
	private static final long serialVersionUID = -681643631408265088L;

	static Logger log = Logger.getLogger(UtentiBean.class);
	
	List<UtentePOJO> allUtenti;
	UtentePOJO utenteSelezionato;
	
	@PostConstruct
	public void init() {
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();
		utenteSelezionato = new UtentePOJO();
	}

	public List<UtentePOJO> getAllUtenti() {
		return allUtenti;
	}

	public void setAllUtenti(List<UtentePOJO> allUtenti) {
		this.allUtenti = allUtenti;
	}

	public UtentePOJO getUtenteSelezionato() {
		return utenteSelezionato;
	}

	public void setUtenteSelezionato(UtentePOJO utenteSelezionato) {
		this.utenteSelezionato = utenteSelezionato;
	}


}
