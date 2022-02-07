package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.dao.ArticoloDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class AreaRiservataBean implements Serializable {
	
	static Logger log = Logger.getLogger(AreaRiservataBean.class);

	private static final long serialVersionUID = 1L;
	
	private Articolo addArticolo;
	List<Articolo> viewAllArticoli;
	List<Utente> viewAllUtenti;
	
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
		
		log.info("-->Finito Add Articoli, viewAddArticoli="+viewAddArticoli);
		
	}
	
	public void insertArticolo() {
		log.info("--> Inserimento articolo.");
	}
	
	public void showViewArticoli() {
		log.info("-->View Articoli");
		viewAllArticoli = ArticoloDAO.getInstance().selectAllArticoli();
		
		viewAddArticoli = false;
		viewArticoli = true;
		viewUtenti = false;
		viewOrdini = false;
	}
	
	public void showViewUtenti() {
		
		log.info("-->View Utenti");
		viewAllUtenti = UtenteDAO.getInstance().selectAllUtenti();
		
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

	public List<Articolo> getViewAllArticoli() {
		return viewAllArticoli;
	}

	public void setViewAllArticoli(List<Articolo> viewAllArticoli) {
		this.viewAllArticoli = viewAllArticoli;
	}

	public List<Utente> getViewAllUtenti() {
		return viewAllUtenti;
	}

	public void setViewAllUtenti(List<Utente> viewAllUtenti) {
		this.viewAllUtenti = viewAllUtenti;
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
