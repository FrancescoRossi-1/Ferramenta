package it.exolab.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.IterationStatus;

import it.exolab.constants.Constants;
import it.exolab.dao.AllegatoDAO;
import it.exolab.dao.ArticoloDAO;
import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.GenericException;
import it.exolab.exception.GenericFileException;
import it.exolab.exception.OggettoEsistente;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ArticoliBean implements Serializable {

	private static final long serialVersionUID = -7173788432598696339L;

	static Logger log = LogManager.getLogger(ArticoliBean.class);

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	@ManagedProperty("#{allegatiBean}")
	private AllegatiBean allegatiBean;

	private List<Articolo> allArticoli;
	private Map<Articolo,List<String>> articoloEImmagini;
	private List<String> immaginiArticoloSelezionato;


	private List<String> stringPhotos;
	private Articolo articoloSelezionato;
	private Integer quantitaArticolo;
	private List<Integer> quantitaArticoloModifica;

	private Articolo addArticolo;


	@PostConstruct
	public void init() {
		
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli();
		articoloEImmagini = new HashMap<>();
		addArticolo = new Articolo();
		for (Articolo articolo : allArticoli) {
			Integer count = 0;
			for ( Allegato allegato : allegatiBean.getAllAllegati() ) {
				if( allegato.getId_articolo() == articolo.getId_articolo() ) {
					if( count == 0 ) {						
						articoloEImmagini.put(articolo, new ArrayList<String>());
					}
					articoloEImmagini.get(articolo).add(allegatiBean.getAllegatiAndStringConvert().get(allegato));
					log.info(articoloEImmagini.get(articolo).toString());
					count++;
				}
			}
		}
		

		quantitaArticoloModifica = new ArrayList<>(articoloEImmagini.size());

	}

	public void insertArticolo() {
		log.info("--> Inserimento articolo.");

		try {
			
			ValidationService.checkParametersArticolo(getAddArticolo());
			ValidationService.checkEqualsArticolo(getAddArticolo(), getAllArticoli());

			ValidationService.checkImagesAllegati(allegatiBean.getImmagini());

			ArticoloDAO.getInstance().insertArticolo(addArticolo);

			AllegatoDAO.insertAll(allegatiBean.createListAllegati(addArticolo));

			allegatiBean.init();
			init();
			sessionBean.setSuccessMessage(Constants.Messages.SUCCESFULLY_INSTERTED_PRODUCT);		

		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( OggettoEsistente oe ) {
			sessionBean.setErrorMessage(oe.getMessage());
		} catch ( FileImmagineNonSupportato fins ) {
			sessionBean.setErrorMessage(fins.getMessage());
		} catch ( GenericFileException gfe ) {
			sessionBean.setErrorMessage(gfe.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}

	}
	


	public void deleteArticolo(Long idArticolo) {

		try {
			log.info("Eliminazione entrato");
			ArticoloDAO.getInstance().deleteArticoloFromId(idArticolo);
			init();
			
			if(sessionBean.getLoggedUser().getIsAdmin()) {
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			CarrelloBean carrelloBean = (CarrelloBean) FacesContext.getCurrentInstance().getApplication()
			    .getELResolver().getValue(elContext, null, "carrelloBean");
			
			carrelloBean.init();
			}
			
			sessionBean.setSuccessMessage(Constants.Messages.DELETE_ARTICOLO_SUCCESS);
			PrimeFaces.current().ajax().update("menuForm:tabView:menuAreaRiservata:gestioneArticoli");
			

		} catch ( Exception e ) {
			
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}

	}
	
	public void aggiungiQuantitaArticolo(Articolo articolo) {
	
		
		try {
			
			log.info("Quantita Disponibile modificata -> " + articolo.getQuantita_disponibile());			
			
			if(articolo.getQuantita_disponibile() <= 0) {
				throw new GenericException(Constants.ExceptionMessages.QUANTITA_ARTICOLO_MINORE);
			}
			
			if(articolo.getQuantita_disponibile() > 150) {
				throw new GenericException(Constants.ExceptionMessages.QUANTITA_ARTICOLO_MAGGIORE);
			}
			
			ArticoloDAO.getInstance().updateQuantitaDisponibile(articolo);
			
			init();
			
			PrimeFaces.current().ajax().update("menuForm:tabView:menuAreaRiservata:gestioneArticoli");
			
		} catch ( GenericException ge ) {
			
			sessionBean.setErrorMessage(ge.getMessage());
			
		} catch ( Exception e ) {
			
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			
			log.info(e.getMessage(), e);
			
		}
		
	}
	
	public ArrayList<Entry<Articolo, List<String>>> getImages() {
		Set<Map.Entry<Articolo, List<String>>> articoliSet = articoloEImmagini.entrySet();
		return new ArrayList<Map.Entry<Articolo,List<String>>>(articoliSet);
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public AllegatiBean getAllegatiBean() {
		return allegatiBean;
	}

	public void setAllegatiBean(AllegatiBean allegatiBean) {
		this.allegatiBean = allegatiBean;
	}
	
	public List<Articolo> getAllArticoli() {
		return allArticoli;
	}

	public void setAllArticoli(List<Articolo> allArticoli) {
		this.allArticoli = allArticoli;
	}

	public Map<Articolo, List<String>> getArticoloEImmagini() {
		return articoloEImmagini;
	}

	public void setArticoloEImmagini(Map<Articolo, List<String>> articoloEImmagini) {
		this.articoloEImmagini = articoloEImmagini;
	}

	public List<String> getImmaginiArticoloSelezionato() {
		return immaginiArticoloSelezionato;
	}

	public void setImmaginiArticoloSelezionato(List<String> immaginiArticoloSelezionato) {
		this.immaginiArticoloSelezionato = immaginiArticoloSelezionato;
	}

	public List<String> getStringPhotos() {
		return stringPhotos;
	}

	public void setStringPhotos(List<String> stringPhotos) {
		this.stringPhotos = stringPhotos;
	}

	public Articolo getArticoloSelezionato() {
		return articoloSelezionato;
	}

	public void setArticoloSelezionato(Articolo articoloSelezionato) {
		this.articoloSelezionato = articoloSelezionato;		
		setImmaginiArticoloSelezionato(articoloEImmagini.get(articoloSelezionato));
	}

	public Integer getQuantitaArticolo() {
		return quantitaArticolo;
	}

	public void setQuantitaArticolo(Integer quantitaArticolo) {
		this.quantitaArticolo = quantitaArticolo;
	}

	public List<Integer> getQuantitaArticoloModifica() {
		return quantitaArticoloModifica;
	}

	public void setQuantitaArticoloModifica(List<Integer> quantitaArticoloModifica) {
		this.quantitaArticoloModifica = quantitaArticoloModifica;
	}

	public Articolo getAddArticolo() {
		return addArticolo;
	}

	public void setAddArticolo(Articolo addArticolo) {
		this.addArticolo = addArticolo;
	}

}
