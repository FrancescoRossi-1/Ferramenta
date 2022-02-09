package it.exolab.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.log4j.Logger;

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
