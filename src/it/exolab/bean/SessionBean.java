package it.exolab.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.pojo.UtentePOJO;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
	
	private static final long serialVersionUID = -5201806537959508743L;

	static Logger log = LogManager.getLogger(SessionBean.class); 
	
	private UtentePOJO loggedUser = null; //user in sessione, se null l'utente non è loggato
		
	public void logout() {
		System.out.println("Logout.");
		this.loggedUser = null;
	}
	
	public UtentePOJO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UtentePOJO loggedUser) {
		this.loggedUser = loggedUser;
	}

	public void setSuccessMessage(String successMessage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successo", successMessage));
	}

	public void setErrorMessage(String errorMessage) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", errorMessage));
	}

}
