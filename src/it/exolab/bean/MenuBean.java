package it.exolab.bean;

import java.io.Serializable;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;

import it.exolab.constants.Constants;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -4793276709821120300L;

	private final static Logger log = LogManager.getLogger(MenuBean.class);
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	private Boolean visibileOrdine = false;
	private int tabIndex = 0;



	public void onTabChangeInserimento(TabChangeEvent<?> event) {
		
		log.info("--->>>OnTabChange");
		
		String currentTabId = event.getTab().getId();
		log.info("Current tab id: " + event.getTab().getId());
		
		/*Se l'utente sta uscendo dall'ordine allora rendilo invisibile*/
		if(!Constants.Tabs.TAB_ORDINE.equals(currentTabId)) {
			setVisibileOrdine(false);
			PrimeFaces.current().ajax().update("menuForm:tabView"); //aggiornamento
		}

		if(Constants.Tabs.TAB_LOGOUT.equals(currentTabId)) {
			sessionBean.logout();
			PrimeFaces.current().ajax().update("menuForm:tabView");
		}

		if (Constants.Tabs.TAB_HOMEPAGE.equals(currentTabId) ) {
			this.tabIndex = Constants.Tabs.ID_TAB_HOMEPAGE;
		}

		if (Constants.Tabs.TAB_LOGIN .equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_LOGIN;
		}

		if (Constants.Tabs.TAB_SIGNUP.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_SIGNUP;
		}

		if (Constants.Tabs.TAB_ARTICOLI.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_ARTICOLI;
		}
		
		if (Constants.Tabs.TAB_AREA_RISERVATA.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_AREA_RISERVATA;
		}
		
		if (Constants.Tabs.TAB_CARRELLO.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_CARRELLO;
		}
		
		if(Constants.Tabs.TAB_ORDINE.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_ORDINE;
		}

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	

	public Boolean getVisibileOrdine() {
		return visibileOrdine;
	}

	public void setVisibileOrdine(Boolean visibileOrdine) {
		this.visibileOrdine = visibileOrdine;
		if(visibileOrdine) {
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			OrdiniBean ordiniBean = (OrdiniBean) FacesContext.getCurrentInstance().getApplication()
			    .getELResolver().getValue(elContext, null, "ordiniBean");
			ordiniBean.init();
			this.tabIndex = Constants.Tabs.ID_TAB_ORDINE;
		}
		
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

}
