package it.exolab.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;

import it.exolab.constants.Constants;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -4793276709821120300L;

	private final static Logger log = Logger.getLogger(MenuBean.class);

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	private int tabIndex = 0;



	public void onTabChangeInserimento(TabChangeEvent<?> event) {
		
		log.info("--->>>OnTabChange");
		
		//checkMessaggiDaEliminare
		if(sessionBean.getSuccessMessage() != null) {
			sessionBean.setSuccessMessage(null);
			PrimeFaces.current().ajax().update("menuForm");
			log.info("Messaggio successo settato null.");
		}
		
		if(sessionBean.getErrorMessage() != null) {
			sessionBean.setErrorMessage(null);
			PrimeFaces.current().ajax().update("menuForm");
			log.info("Messaggio successo settato null.");
		}
		

		String currentTabId = event.getTab().getId();
		log.debug("Current tab_inserimento id: " + event.getTab().getId());

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

		if (Constants.Tabs.TAB_CATEGORIE.equals(currentTabId) ) {
			this.tabIndex = Constants.Tabs.ID_TAB_CATEGORIE;
		}

		if (Constants.Tabs.TAB_ARTICOLI.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_ARTICOLI;
		}
		
		if (Constants.Tabs.TAB_AREA_RISERVATA.equals(currentTabId)) {
			this.tabIndex = Constants.Tabs.ID_TAB_AREA_RISERVATA;
		}

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

}
