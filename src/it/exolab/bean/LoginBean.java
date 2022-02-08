package it.exolab.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.UtenteNonEsistente;
import it.exolab.service.LoginService;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	static Logger log = Logger.getLogger(LoginBean.class); 

	private static final long serialVersionUID = 8439937226512877090L;

	private Utente loginUser;

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	@PostConstruct
	public void init() {

		log.info("--> Init");
		loginUser = new Utente();

	}

	public void loginUtente() {

		log.info("--> Login");

		try {

			LoginService.checkParameters(loginUser);
			LoginService.checkUser(loginUser);
			
			loginUser = LoginService.selectUser(loginUser);
			
			sessionBean.setLoggedUser(loginUser);
			sessionBean.setSuccessMessage(Constants.Messages.LOGIN_AVVENUTO);

		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());	

		} catch ( UtenteNonEsistente une ) {

			sessionBean.setErrorMessage(une.getMessage());	
			log.info(une.getMessage(), une);

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		} finally {
			PrimeFaces.current().ajax().update("messageDiv");
		}

	}

	public Utente getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Utente loginUser) {
		this.loginUser = loginUser;
	}


	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}




}
