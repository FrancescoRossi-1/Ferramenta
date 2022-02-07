package it.exolab.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.ProvinciaDAO;
import it.exolab.dto.Provincia;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.UtenteEsistente;
import it.exolab.service.SignUpService;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class SignUpBean implements Serializable {

	private static final long serialVersionUID = 7346257810844237669L;

	static Logger log = Logger.getLogger(SignUpBean.class);

	private Utente user = new Utente();
	private List<Provincia> provinceEstrapolate;

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	@PostConstruct
	public void init() {
		log.info("-->INIT");
		this.provinceEstrapolate = ProvinciaDAO.findAll();
		this.provinceEstrapolate.forEach(prov -> log.info(prov.toString()));
	}

	public void registraUtente() {
		log.info("-->Entrato in registrazione.");

		try {
			SignUpService.checkParameters(user);
			SignUpService.setUtilityParameters(user);
			SignUpService.insertUser(user);
			sessionBean.setSuccessMessage(Constants.Messages.REGISTRAZIONE_AVVENUTA);
			log.info("--->Utente Registrato.");
		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( FormatoErrato fe ) {
			sessionBean.setErrorMessage(fe.getMessage());
		} catch ( UtenteEsistente ue ) {
			sessionBean.setErrorMessage(ue.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		} finally {
			PrimeFaces.current().ajax().update("messageDiv");
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

	public List<Provincia> getProvinceEstrapolate() {
		return provinceEstrapolate;
	}

	public void setProvinceEstrapolate(List<Provincia> provinceEstrapolate) {
		this.provinceEstrapolate = provinceEstrapolate;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

}
