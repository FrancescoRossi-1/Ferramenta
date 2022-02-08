package it.exolab.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
	
	private static final long serialVersionUID = -5201806537959508743L;

	static Logger log = Logger.getLogger(SessionBean.class); 
	
	private Utente loggedUser = null; //user in sessione, se null l'utente non è loggato

	private String successMessage = null;
	private String errorMessage = null;
	
	public void logout() {
		//log.info("--> Logout");
		System.out.println("Logout.");
		this.loggedUser = null;
	}
	
	public void refreshOrdini() {
		
		//TODO SELECT ORDINI
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Utente getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Utente loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
