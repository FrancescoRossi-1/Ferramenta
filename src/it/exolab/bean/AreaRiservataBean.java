package it.exolab.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.AllegatoDAO;
import it.exolab.dao.ArticoloDAO;

import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.GenericFileException;
import it.exolab.exception.OgettoEsistente;
import it.exolab.service.ArticoliService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class AreaRiservataBean implements Serializable {

	static Logger log = Logger.getLogger(AreaRiservataBean.class);

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty("#{articoliBean}")
	private ArticoliBean articoliBean;
	
	@ManagedProperty("#{categorieBean}")
	private CategorieBean categorieBean;

	@PostConstruct
	public void init() {
	}


	public void insertArticolo() {
		log.info("--> Inserimento articolo.");

		try {
			
			ArticoliService.checkParameters(articoliBean.getAddArticolo());
			ArticoliService.checkEquals(articoliBean.getAddArticolo(), articoliBean.getAllArticoli());
			
			articoliBean.checkImages(articoliBean.getArticoloImages());
			
			ArticoloDAO.getInstance().insertArticolo(articoliBean.getAddArticolo());
			
			articoliBean.fillImageList(articoliBean.getAddArticolo(),articoliBean.getArticoloImages());	
			AllegatoDAO.insertAll(articoliBean.getAddArticolo().getAllegatiAppartenenti());
			
			articoliBean.init();
			sessionBean.setSuccessMessage(Constants.Messages.SUCCESFULLY_INSTERTED_PRODUCT);		
			
		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( OgettoEsistente oe ) {
			sessionBean.setErrorMessage(oe.getMessage());
		} catch ( FileImmagineNonSupportato fins ) {
			sessionBean.setErrorMessage(fins.getMessage());
		} catch ( GenericFileException gfe ) {
			sessionBean.setErrorMessage(gfe.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		} finally {
			PrimeFaces.current().ajax().update("messageDiv");
		}

	}
	
	public void deleteArticolo(Long idArticolo) {
		
		try {
			
			ArticoloDAO.getInstance().deleteArticoloFromId(idArticolo);
			articoliBean.init();
			sessionBean.setSuccessMessage(Constants.Messages.DELETE_ARTICOLO_SUCCESS);
			PrimeFaces.current().ajax().update("menuForm:tabView:menuAreaRiservata:gestioneArticoli");
			
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
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

	public ArticoliBean getArticoliBean() {
		return articoliBean;
	}


	public void setArticoliBean(ArticoliBean articoliBean) {
		this.articoliBean = articoliBean;
	}


	public CategorieBean getCategorieBean() {
		return categorieBean;
	}


	public void setCategorieBean(CategorieBean categorieBean) {
		this.categorieBean = categorieBean;
	}


}
