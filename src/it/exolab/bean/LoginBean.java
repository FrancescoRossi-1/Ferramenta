package it.exolab.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.constants.Constants;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.UtenteNonEsistente;
import it.exolab.service.ValidationService;

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

		loginUser = new Utente();

	}

	public void loginUtente() {

		try {
			
			sessionBean.setLoading(true);
			
			ValidationService.checkParametersLogin(loginUser);
			ValidationService.checkExistingUserLogin(loginUser);
			
			loginUser = UtenteDAO.getInstance().selectUser(loginUser);
			
			sessionBean.setLoggedUser(loginUser);			
			sessionBean.setSuccessMessage(Constants.Messages.LOGIN_AVVENUTO);
			
			sessionBean.setLoading(false);

		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());	

		} catch ( UtenteNonEsistente une ) {

			sessionBean.setErrorMessage(une.getMessage());	

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

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
