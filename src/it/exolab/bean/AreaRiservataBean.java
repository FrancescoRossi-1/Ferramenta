package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import it.exolab.dao.ArticoloDAO;
import it.exolab.dao.CategoriaDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.Categoria;
import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class AreaRiservataBean implements Serializable {
	
	static Logger log = Logger.getLogger(AreaRiservataBean.class);

	private static final long serialVersionUID = 1L;
	
	private Articolo addArticolo;
	
	List<Articolo> allArticoli;
	List<Utente> allUtenti;
	List<Categoria> allCategorie;
	
	@PostConstruct
	public void init() {
		//estrapola tutti gli articoli
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli(); 
		
		//estrapola tutti gli utenti
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();
		
		//estrapola tutti gli ordini
		//TODO 
	}

	
	public void insertArticolo() {
		log.info("--> Inserimento articolo.");
		
		log.info(addArticolo.toString());
		
	}


	public Articolo getAddArticolo() {
		return addArticolo;
	}

	public void setAddArticolo(Articolo addArticolo) {
		this.addArticolo = addArticolo;
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
