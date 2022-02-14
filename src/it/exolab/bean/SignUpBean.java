package it.exolab.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;


import it.exolab.constants.Constants;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.OggettoEsistente;
import it.exolab.service.ValidationService;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class SignUpBean implements Serializable {

	private static final long serialVersionUID = 7346257810844237669L;

	static Logger log = Logger.getLogger(SignUpBean.class);

	private Utente user = new Utente();

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty("#{indirizziBean}")
	private IndirizziBean indirizzoBean;
	
	@ManagedProperty("#{provinceBean}")
	private ProvinceBean provinceBean;
	
	@ManagedProperty("#{utentiBean}")
	private UtentiBean utentiBean;

	@PostConstruct
	public void init() {
		user = new Utente();
	}

	public void registraUtente() {
		log.info("-->Entrato in registrazione.");

		try {
			
			log.info(user.toString());
			log.info(indirizzoBean.getAddIndirizzo().toString());
			log.info(provinceBean.getIdProvinciaSelezionata());
			
			ValidationService.checkParametersSignUp(user,indirizzoBean.getAddIndirizzo(),provinceBean.getIdProvinciaSelezionata());
			
			ValidationService.checkExistingUserSignUp(utentiBean.getAllUtenti(), user);
			
			indirizzoBean.getAddIndirizzo().setId_indirizzo(indirizzoBean.insertIndirizzo()); //logica di inserimento indirizzo che ritorna l'id di esso
			
			//utility parameters
			user.setData_iscrizione(new Date(System.currentTimeMillis()));
			user.setIsAdmin(false);
			
			UtenteDAO.getInstance().insertUser(user,indirizzoBean.getAddIndirizzo());
			
			sessionBean.setSuccessMessage(Constants.Messages.REGISTRAZIONE_AVVENUTA);
			
			init(); //ricarica bean
			log.info("--->Utente Registrato.");
			
		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( FormatoErrato fe ) {
			sessionBean.setErrorMessage(fe.getMessage());
		} catch ( OggettoEsistente ue ) {
			sessionBean.setErrorMessage(ue.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}

	}

	public void pulisciCampi() {
		this.user = new Utente();
		log.info("Pulizia effettuata.");
	}

	public Utente getUser() {
		return user;
	}

	public void setUser(Utente user) {
		this.user = user;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public ProvinceBean getProvinceBean() {
		return provinceBean;
	}

	public void setProvinceBean(ProvinceBean provinceBean) {
		this.provinceBean = provinceBean;
	}

	public UtentiBean getUtentiBean() {
		return utentiBean;
	}

	public void setUtentiBean(UtentiBean utentiBean) {
		this.utentiBean = utentiBean;
	}

	public IndirizziBean getIndirizzoBean() {
		return indirizzoBean;
	}

	public void setIndirizzoBean(IndirizziBean indirizzoBean) {
		this.indirizzoBean = indirizzoBean;
	}

}
