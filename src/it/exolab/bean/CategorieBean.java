package it.exolab.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.CategoriaDAO;
import it.exolab.dto.Categoria;
import it.exolab.exception.CampoRichiesto;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class CategorieBean {
	
	static Logger log = LogManager.getLogger(CategorieBean.class);
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	List<Categoria> allCategorie;
	Categoria addCategoria;
	
	@PostConstruct
	public void init() {
		addCategoria = new Categoria();
		
		allCategorie = CategoriaDAO.getInstance().selectAllCategorie();
		allCategorie.forEach(cat -> log.info(cat));
	}
	
	public void insertCategoria() {
		
		try {
			
			ValidationService.checkParametersCategoria(addCategoria);
			ValidationService.checkExistingCategoria(addCategoria, allCategorie);
			
			CategoriaDAO.getInstance().insertCategoria(addCategoria);
			
			sessionBean.setSuccessMessage(Constants.Messages.SUCCESFULLY_INSTERTED_CATEGORY);
			
			init(); //ricarica tutte le categorie
			
		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}
		
	}
	
	public void deleteCategoria(Long idCategoria) {
		
		try {
			
			CategoriaDAO.getInstance().deleteCategoriaFromId(idCategoria);
			init(); //ricarica le categorie
			sessionBean.setSuccessMessage(Constants.Messages.DELETE_CATEGORIA_SUCCESS);
			PrimeFaces.current().ajax().update("menuForm:tabView:menuAreaRiservata:gestioneCategorie");
			
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
		}
		
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<Categoria> getAllCategorie() {
		return allCategorie;
	}

	public void setAllCategorie(List<Categoria> allCategorie) {
		this.allCategorie = allCategorie;
	}

	public Categoria getAddCategoria() {
		return addCategoria;
	}

	public void setAddCategoria(Categoria addCategoria) {
		this.addCategoria = addCategoria;
	}

}
