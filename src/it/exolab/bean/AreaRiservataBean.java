package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

import it.exolab.constants.Constants;
import it.exolab.dao.ArticoloDAO;
import it.exolab.dao.CategoriaDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.Categoria;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.service.ArticoliService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class AreaRiservataBean implements Serializable {

	static Logger log = Logger.getLogger(AreaRiservataBean.class);

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	private Articolo addArticolo;
	private UploadedFile articoloImage;

	List<Articolo> allArticoli;
	List<Utente> allUtenti;
	List<Categoria> allCategorie;

	@PostConstruct
	public void init() {

		addArticolo = new Articolo();

		//estrapola tutte le categorie
		allCategorie = CategoriaDAO.getInstance().selectAllCategorie();

		//estrapola tutti gli articoli
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli(); 

		//estrapola tutti gli utenti
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();

		//estrapola tutti gli ordini
		//TODO 
	}


	public void insertArticolo() {
		log.info("--> Inserimento articolo.");

		try {
			ArticoliService.checkParameters(addArticolo);
			ArticoliService.checkImage(articoloImage);
			ArticoliService.storeFile(articoloImage);


		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( FileImmagineNonSupportato fins ) {
			sessionBean.setErrorMessage(fins.getMessage());
		}catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		} finally {
			PrimeFaces.current().ajax().update("messageDiv");
		}

	}


	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public Articolo getAddArticolo() {
		return addArticolo;
	}

	public void setAddArticolo(Articolo addArticolo) {
		this.addArticolo = addArticolo;
	}

	public UploadedFile getArticoloImage() {
		return articoloImage;
	}


	public void setArticoloImage(UploadedFile articoloImage) {
		this.articoloImage = articoloImage;
	}


	public List<Articolo> getAllArticoli() {
		return allArticoli;
	}

	public void setAllArticoli(List<Articolo> allArticoli) {
		this.allArticoli = allArticoli;
	}

	public List<Utente> getAllUtenti() {
		return allUtenti;
	}

	public void setAllUtenti(List<Utente> allUtenti) {
		this.allUtenti = allUtenti;
	}


	public List<Categoria> getAllCategorie() {
		return allCategorie;
	}

	public void setAllCategorie(List<Categoria> allCategorie) {
		this.allCategorie = allCategorie;
	}





}
