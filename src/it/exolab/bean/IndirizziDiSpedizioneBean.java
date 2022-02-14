package it.exolab.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.IndirizzoDiSpedizioneDAO;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.OggettoEsistente;
import it.exolab.pojo.IndirizzoDiSpedizionePOJO;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class IndirizziDiSpedizioneBean implements Serializable {

	static Logger log = Logger.getLogger(IndirizziDiSpedizioneBean.class); 

	private static final long serialVersionUID = -5987437588436053809L;

	@ManagedProperty ( "#{sessionBean}" )
	private SessionBean sessionBean;

	@ManagedProperty ( "#{indirizziBean}" )
	private IndirizziBean indirizziBean;

	private List<IndirizzoDiSpedizione> allIndirizziDiSpedizione;
	private List<IndirizzoDiSpedizione> indirizziSpedizioneUtente;
	private Map<Indirizzo, IndirizzoDiSpedizione> indirizzoEIndirizzoSpedizioneUtente;
	private ArrayList<Map.Entry<Indirizzo,IndirizzoDiSpedizione>> indirizziMap;

	private IndirizzoDiSpedizione addIndirizzoDiSpedizione;
	private IndirizzoDiSpedizionePOJO indirizzoDiSpedizioneSelezionato;

	@PostConstruct
	public void init() {

		log.info("--> INIT indirizzo di spedizione");

		addIndirizzoDiSpedizione = new IndirizzoDiSpedizione();
		indirizzoDiSpedizioneSelezionato = new IndirizzoDiSpedizionePOJO();
		indirizzoEIndirizzoSpedizioneUtente = new HashMap<>();

		allIndirizziDiSpedizione = IndirizzoDiSpedizioneDAO.getInstance().findAll(); //TODO ?????
		indirizziSpedizioneUtente = IndirizzoDiSpedizioneDAO.getInstance().findAllByUserId(sessionBean.getLoggedUser());

		/*Riempimento indirizzo e indirizzi spedizione*/
		for ( Indirizzo indirizzo : indirizziBean.getAllIndirizzi()) {
			for (IndirizzoDiSpedizione indirizzoDiSpedizione : allIndirizziDiSpedizione) {
				if(indirizzo.getId_indirizzo().equals(indirizzoDiSpedizione.getId_indirizzo())) {
					indirizzoEIndirizzoSpedizioneUtente.put(indirizzo, indirizzoDiSpedizione);
				}
			}
		}

		Set<Map.Entry<Indirizzo, IndirizzoDiSpedizione>> indirizziSet = indirizzoEIndirizzoSpedizioneUtente.entrySet();
		indirizziMap = new ArrayList<Map.Entry<Indirizzo,IndirizzoDiSpedizione>>(indirizziSet);

	}


	public void insertIndirizzoDiSpedizione() {

		try {

			Long idIndirizzo = indirizziBean.insertIndirizzo();

			/*Inizializzazione ids*/
			addIndirizzoDiSpedizione.setId_indirizzo(idIndirizzo);
			addIndirizzoDiSpedizione.setId_utente(sessionBean.getLoggedUser().getId());

			ValidationService.checkParametersIndirizzoDiSpedizione(addIndirizzoDiSpedizione); //validazione

			/*Controlla se l'indirizzo è già presente*/
			if(indirizziSpedizioneUtente.contains(addIndirizzoDiSpedizione)) {
				throw new OggettoEsistente(addIndirizzoDiSpedizione);
			}

			IndirizzoDiSpedizioneDAO.getInstance().insertIndirizzoDiSpedizione(addIndirizzoDiSpedizione, sessionBean.getLoggedUser());

			init(); 
			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionOrdine:accorditionIndirizziSpedizione"); //aggiornamento pagina visualizzazzione indirizzi
			sessionBean.setSuccessMessage(Constants.Messages.INSERT_INDIRIZZO_SPEDIZIONE_SUCCESS);

		} catch ( CampoRichiesto cr ) {

			sessionBean.setErrorMessage(cr.getMessage());

		} catch ( FormatoErrato fe ) {

			sessionBean.setErrorMessage(fe.getMessage());

		} catch ( OggettoEsistente oe ) {

			sessionBean.setErrorMessage(oe.getMessage());

		} catch (Exception e) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		}

	}

	public void deleteIndirizzoDiSpedizione( IndirizzoDiSpedizione indirizzoDiSpedizione ) {

		try {

			IndirizzoDiSpedizioneDAO.getInstance().deleteIndirizzoDiSpedizioneFromId(indirizzoDiSpedizione);
			init();
			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionOrdine:accorditionIndirizziSpedizione:scrollerIndirizziSpedizione");

			sessionBean.setSuccessMessage(Constants.Messages.DELETE_INDIRIZZO_SPEDIZIONE_SUCCESS);

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		}

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public IndirizziBean getIndirizziBean() {
		return indirizziBean;
	}

	public void setIndirizziBean(IndirizziBean indirizziBean) {
		this.indirizziBean = indirizziBean;
	}

	public List<IndirizzoDiSpedizione> getAllIndirizziDiSpedizione() {
		return allIndirizziDiSpedizione;
	}

	public void setAllIndirizziDiSpedizione(List<IndirizzoDiSpedizione> allIndirizziDiSpedizione) {
		this.allIndirizziDiSpedizione = allIndirizziDiSpedizione;
	}

	public List<IndirizzoDiSpedizione> getIndirizziSpedizioneUtente() {
		return indirizziSpedizioneUtente;
	}

	public void setIndirizziSpedizioneUtente(List<IndirizzoDiSpedizione> indirizziSpedizioneUtente) {
		this.indirizziSpedizioneUtente = indirizziSpedizioneUtente;
	}

	public IndirizzoDiSpedizione getAddIndirizzoDiSpedizione() {
		return addIndirizzoDiSpedizione;
	}

	public void setAddIndirizzoDiSpedizione(IndirizzoDiSpedizione addIndirizzoDiSpedizione) {
		this.addIndirizzoDiSpedizione = addIndirizzoDiSpedizione;
	}

	public IndirizzoDiSpedizionePOJO getIndirizzoDiSpedizioneSelezionato() {
		return indirizzoDiSpedizioneSelezionato;
	}

	public void setIndirizzoDiSpedizioneSelezionato(Indirizzo indirizzo, IndirizzoDiSpedizione indirizzoDiSpedizione) {

		/*L'utente vuole cambiare indirizzo di spedizione*/
		if(indirizzo == null && indirizzoDiSpedizione == null) {
			indirizzoDiSpedizioneSelezionato = new IndirizzoDiSpedizionePOJO();
		}else {
			/*Casting manuale da dto a pojo*/
			indirizzoDiSpedizioneSelezionato.setIndirizzoDiRiferimento(indirizzo);
			indirizzoDiSpedizioneSelezionato.setUtenteDiRiferimento(sessionBean.getLoggedUser());
			indirizzoDiSpedizioneSelezionato.setId(indirizzoDiSpedizione.getId_indirizzo_spedizione());
			indirizzoDiSpedizioneSelezionato.setScala(indirizzoDiSpedizione.getScala());
			indirizzoDiSpedizioneSelezionato.setInterno(indirizzoDiSpedizione.getInterno());
		}
	}

	public Map<Indirizzo, IndirizzoDiSpedizione> getIndirizzoEIndirizzoSpedizioneUtente() {
		return indirizzoEIndirizzoSpedizioneUtente;
	}

	public void setIndirizzoEIndirizzoSpedizioneUtente(
			Map<Indirizzo, IndirizzoDiSpedizione> indirizzoEIndirizzoSpedizioneUtente) {
		this.indirizzoEIndirizzoSpedizioneUtente = indirizzoEIndirizzoSpedizioneUtente;
	}

	public ArrayList<Entry<Indirizzo, IndirizzoDiSpedizione>> getIndirizziMap() {
		return indirizziMap;
	}

}
