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

import org.apache.log4j.Logger;

import it.exolab.constants.Constants;
import it.exolab.dao.AllegatoDAO;
import it.exolab.dao.ArticoloDAO;
import it.exolab.dao.CarrelloDAO;
import it.exolab.dao.CarrelloEArticoloDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;
import it.exolab.dto.Carrello;
import it.exolab.dto.CarrelloEArticolo;
import it.exolab.exception.GenericCarrelloException;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class CarrelloBean implements Serializable {

	private static final long serialVersionUID = 6699902300057086453L;

	static Logger log = Logger.getLogger( CarrelloBean.class );

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
			log.info("Carrello null");
			carrelloUtente = CarrelloDAO.getInstance().createNewCarrello(carrelloUtente);
			log.info("Carrello creato");
			UtenteDAO.getInstance().assegnaNuovoCarrello(carrelloUtente,sessionBean.getLoggedUser());
			log.info("Update effettuato");
		} else {

			righeUtente = CarrelloEArticoloDAO.getInstance().selectRigheFromIdCarrello(carrelloUtente);
			
			for (CarrelloEArticolo riga : righeUtente) {
				for (Articolo articolo : articoliBean.getAllArticoli()) {
					if(riga.getId_articolo() == articolo.getId_articolo()) {
						articoliEQuantita.put(articolo, riga.getQuanita());
					}
				}
			}

			articoliEQuantita.forEach( (art, quant) -> log.info("\nQuantita -> " + quant + "\nArticolo -> " + art.toString()));

		}

	}

	public void insertArticoloInCarrello() {

		try {
			log.info("Entrato");

			Articolo articoloDaAggiungere = articoliBean.getArticoloSelezionato();
			Integer quantita = articoliBean.getQuantitaArticolo();

			ValidationService.checkQuantitaArticolo(articoloDaAggiungere, quantita);

			CarrelloEArticoloDAO.getInstance().insertRiga(articoloDaAggiungere,quantita,carrelloUtente);
			CarrelloDAO.getInstance().updateTotaleEUltimaModifica(carrelloUtente);
			
			init();

		} catch ( GenericCarrelloException gce ) {

			sessionBean.setErrorMessage(gce.getMessage());

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);

		}

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
