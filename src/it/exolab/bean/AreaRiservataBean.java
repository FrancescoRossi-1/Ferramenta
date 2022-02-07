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
	
	private UploadedFile imageFile;
	
	private Boolean viewAddArticoli;
	private Boolean viewArticoli;
	private Boolean viewUtenti;
	private Boolean viewOrdini;
	
	@PostConstruct
	public void init() {
		log.info("--->Init");
		viewAddArticoli = false;
		viewArticoli = false;
		viewUtenti = false;
		viewOrdini = false;
	}
	
	public void showAddArticoli() {
		
		log.info("-->Add Articoli");
		
		addArticolo = new Articolo();
		
		viewAddArticoli = true;
		viewArticoli = false;
		viewUtenti = false;
		viewOrdini = false;
		
		//Preparati tutte le categorie;
		allCategorie = CategoriaDAO.getInstance().findAllCategorie();
		log.info("allCategorie->");
		allCategorie.forEach(cat -> log.info(cat));
		
		
	}
	
	public void uploadImage() {
		
		log.info("Immagine ->  " + imageFile);
		
        if (imageFile != null) {
        	
            FacesMessage message = new FacesMessage("Successful", imageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
	}
	
	public void insertArticolo() {
		log.info("--> Inserimento articolo.");
		
		log.info(addArticolo.toString());
		
	}
	
	public void showViewArticoli() {
		log.info("-->View Articoli");
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli();
		
		viewAddArticoli = false;
		viewArticoli = true;
		viewUtenti = false;
		viewOrdini = false;
	}
	
	public void showViewUtenti() {
		
		log.info("-->View Utenti");
		allUtenti = UtenteDAO.getInstance().selectAllUtenti();
		
		viewAddArticoli = false;
		viewArticoli = false;
		viewUtenti = true;
		viewOrdini = false;
		
	}
	
	public void showViewOrdini() {
		
		log.info("-->View ordini");
		//TODO select ordini
		
		viewAddArticoli = false;
		viewArticoli = false;
		viewUtenti = false;
		viewOrdini = true;
		
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

	public UploadedFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(UploadedFile imageFile) {
		this.imageFile = imageFile;
	}

	public List<Categoria> getAllCategorie() {
		return allCategorie;
	}

	public void setAllCategorie(List<Categoria> allCategorie) {
		this.allCategorie = allCategorie;
	}

	public Boolean getViewAddArticoli() {
		return viewAddArticoli;
	}

	public void setViewAddArticoli(Boolean viewAddArticoli) {
		this.viewAddArticoli = viewAddArticoli;
	}

	public Boolean getViewArticoli() {
		return viewArticoli;
	}

	public void setViewArticoli(Boolean viewArticoli) {
		this.viewArticoli = viewArticoli;
	}

	public Boolean getViewUtenti() {
		return viewUtenti;
	}

	public void setViewUtenti(Boolean viewUtenti) {
		this.viewUtenti = viewUtenti;
	}

	public Boolean getViewOrdini() {
		return viewOrdini;
	}

	public void setViewOrdini(Boolean viewOrdini) {
		this.viewOrdini = viewOrdini;
	}
	
	
	

}
