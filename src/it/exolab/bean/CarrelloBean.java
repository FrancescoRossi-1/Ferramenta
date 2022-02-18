package it.exolab.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.CarrelloDAO;
import it.exolab.dao.CarrelloEArticoloDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import it.exolab.dto.CarrelloEArticolo;
import it.exolab.exception.GenericCarrelloException;
import it.exolab.exception.OggettoEsistente;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class CarrelloBean implements Serializable {

	private static final long serialVersionUID = 6699902300057086453L;

	static Logger log = LogManager.getLogger( CarrelloBean.class );

	private Carrello carrelloUtente;
	private List<CarrelloEArticolo> righeUtente;
	private Map<Articolo,Integer> articoliEQuantita;

	@ManagedProperty("#{sessionBean}")
	SessionBean sessionBean;

	@ManagedProperty("#{articoliBean}")
	ArticoliBean articoliBean;

	@PostConstruct
	public void init() {
		
		carrelloUtente = CarrelloDAO.getInstance().findCarrelloByUserId(sessionBean.getLoggedUser());
		articoliEQuantita = new HashMap<>();

		if(carrelloUtente == null) {
			carrelloUtente = new Carrello();
			carrelloUtente = CarrelloDAO.getInstance().createNewCarrello(carrelloUtente);
			UtenteDAO.getInstance().assegnaNuovoCarrello(carrelloUtente,sessionBean.getLoggedUser());
		} 

		righeUtente = CarrelloEArticoloDAO.getInstance().selectRigheFromIdCarrello(carrelloUtente);

		log.info(righeUtente.toString());

		for (CarrelloEArticolo riga : righeUtente) {
			for (Articolo articolo : articoliBean.getAllArticoli()) {
				if(riga.getId_articolo() == articolo.getId_articolo()) {
					log.info(riga.getQuantita());
					articoliEQuantita.put(articolo, riga.getQuantita());
				}
			}
		}

		articoliEQuantita.forEach( (art, quant) -> log.info("\nQuantita -> " + quant + "\nArticolo -> " + art.toString()));


	}

	public void insertArticoloInCarrello() {

		try {
			log.info("Entrato");

			Articolo articoloDaAggiungere = articoliBean.getArticoloSelezionato();
			Integer quantita = articoliBean.getQuantitaArticolo();

			if(articoliEQuantita.containsKey(articoloDaAggiungere)) {
				throw new OggettoEsistente(articoloDaAggiungere);
			}

			ValidationService.checkQuantitaArticolo(articoloDaAggiungere, quantita);

			CarrelloEArticoloDAO.getInstance().insertRiga(articoloDaAggiungere,quantita,carrelloUtente);
			CarrelloDAO.getInstance().updateTotaleEUltimaModifica(carrelloUtente);

			init();

			sessionBean.setSuccessMessage(Constants.Messages.SUCCESSFULLY_INSERTED_PRODUCT_SHOPPING_CART);
			articoliBean.setQuantitaArticolo(0);

		} catch ( OggettoEsistente oe ) {

			sessionBean.setErrorMessage(oe.getMessage());

		} catch ( GenericCarrelloException gce ) {

			sessionBean.setErrorMessage(gce.getMessage());

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);

		}

	}

	public void rimuoviArticolo(Articolo articolo) {

		try {

			CarrelloEArticoloDAO.getInstance().deleteRigaFromIdCarrelloEIdArticolo(carrelloUtente, articolo);
			CarrelloDAO.getInstance().updateTotaleEUltimaModifica(carrelloUtente);
			init();
			sessionBean.setSuccessMessage(Constants.Messages.DELETE_ARTICOLO_FROM_CARRELLO);

			PrimeFaces.current().ajax().update("menuForm:tabView:tabCarrello");

		} catch (Exception e) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}

	}

	public void aumentaDiminuisciQuantitaDiAcquisto( Articolo articolo, Boolean aumentaDiminuisci ) {

		Integer quantitaMomentanea = articoliEQuantita.get(articolo);
		Integer quantitaFinale;

		/*L'utente vuole aumentare la quantit� disponibile*/
		if(aumentaDiminuisci) {
			quantitaFinale = quantitaMomentanea + 1;
		}else {
			/*L'utente vuole diminuire  la quantit� disponibile*/
			quantitaFinale = quantitaMomentanea - 1;
		}

		try {
			
			if(quantitaFinale == 0) {
				rimuoviArticolo(articolo);
			}else if (quantitaFinale > articolo.getQuantita_disponibile()) {
				throw new GenericCarrelloException(Constants.ExceptionMessages.QUANTITA_ARTICOLI_MAGGIORE);
			}

			CarrelloEArticoloDAO.getInstance().updateQuantita(articolo,quantitaFinale, carrelloUtente);
			CarrelloDAO.getInstance().updateTotaleEUltimaModifica(carrelloUtente);
			init();
			PrimeFaces.current().ajax().update("menuForm:tabView:carrello");

		} catch ( GenericCarrelloException gce  ) {
		
			sessionBean.setErrorMessage(gce.getMessage());
			
		}catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);

		}



	}

	public void deleteCarrelloFromUserId() throws Exception {
		CarrelloEArticoloDAO.getInstance().deleteAllFromCarrelloId(carrelloUtente);
		CarrelloDAO.getInstance().deleteFromUserId(carrelloUtente);

		init();

	}

	public List<Map.Entry<Articolo, Integer>> getArticoli() {
		Set<Map.Entry<Articolo, Integer>> articoliSet = articoliEQuantita.entrySet();
		return new ArrayList<Map.Entry<Articolo, Integer>>(articoliSet);
	}

	public Carrello getCarrelloUtente() {
		return carrelloUtente;
	}

	public void setCarrelloUtente(Carrello carrelloUtente) {
		this.carrelloUtente = carrelloUtente;
	}

	public List<CarrelloEArticolo> getRigheUtente() {
		return righeUtente;
	}

	public void setRigheUtente(List<CarrelloEArticolo> righeUtente) {
		this.righeUtente = righeUtente;
	}

	public Map<Articolo, Integer> getArticoliEQuantita() {
		return articoliEQuantita;
	}

	public void setArticoliEQuantita(Map<Articolo, Integer> articoliEQuantita) {
		this.articoliEQuantita = articoliEQuantita;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public ArticoliBean getArticoliBean() {
		return articoliBean;
	}

	public void setArticoliBean(ArticoliBean articoliBean) {
		this.articoliBean = articoliBean;
	}



}
