package it.exolab.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import it.exolab.constants.Constants;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.UtenteNonEsistente;
import it.exolab.pojo.UtentePOJO;
import it.exolab.service.ValidationService;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	static Logger log = LogManager.getLogger(LoginBean.class); 

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
			
			log.info("######## inizio login");
			
			ValidationService.checkParametersLogin(loginUser);
			ValidationService.checkExistingUserLogin(loginUser);
			
			UtentePOJO loggedUser = UtenteDAO.getInstance().selectUser(loginUser);
			
			sessionBean.setLoggedUser(loggedUser);			
			sessionBean.setSuccessMessage(Constants.Messages.LOGIN_AVVENUTO);
			
			log.info("########## login OK");

		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());	

		} catch ( UtenteNonEsistente une ) {

			sessionBean.setErrorMessage(une.getMessage());	

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage());

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
