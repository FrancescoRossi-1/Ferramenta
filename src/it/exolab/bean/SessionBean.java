package it.exolab.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.pojo.UtentePOJO;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
	
	private static final long serialVersionUID = -5201806537959508743L;

	static Logger log = Logger.getLogger(SessionBean.class); 
	
	private UtentePOJO loggedUser = null; //user in sessione, se null l'utente non è loggato

	
	private Boolean loading = false;
	private String successMessage = null;
	private String errorMessage = null;
		
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



	public Boolean getLoading() {
		return loading;
	}

	public void setLoading(Boolean loading) {
		this.loading = loading;
		PrimeFaces.current().ajax().update("menuForm:loading");
		log.info("->>>LOADING");
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		
		if(this.errorMessage != null) {
			this.errorMessage = null;
		}
		
		this.successMessage = successMessage;
		PrimeFaces.current().ajax().update("messageDiv");
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		
		if(this.successMessage != null) {
			this.successMessage = null;
		}
		
		this.errorMessage = errorMessage;
		PrimeFaces.current().ajax().update("messageDiv");
	}

}
