package it.exolab.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import it.exolab.constants.Constants;
import it.exolab.dao.CartaDiCreditoDAO;
import it.exolab.dao.IndirizzoDiSpedizioneDAO;
import it.exolab.dto.CartaDiCredito;
import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.dto.Ordine;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class OrdiniBean {
	
	@ManagedProperty("#{sessionBean}")
	SessionBean sessionBean;
	
	@ManagedProperty("#{menuBean}")
	MenuBean menuBean;
	
	private List<Ordine> allOrdini;
	
	private List<CartaDiCredito> allCarteDiCreditoUtente;
	private List<IndirizzoDiSpedizione> allIndirizziSpedizioneUtente;
	
	private Boolean visibile;
	
	@PostConstruct
	public void init() {
		
		 //TODO SELECT ORDINI
		
		allCarteDiCreditoUtente = CartaDiCreditoDAO.getInstance().findAllByUserId(sessionBean.getLoggedUser());
		allIndirizziSpedizioneUtente = IndirizzoDiSpedizioneDAO.getInstance().findAllByUserId(sessionBean.getLoggedUser());
		
	}
	
	public void inizializzaOrdine() {
		setVisibile(true);
		menuBean.setTabIndex(Constants.Tabs.ID_TAB_ORDINE);
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<Ordine> getAllOrdini() {
		return allOrdini;
	}

	public void setAllOrdini(List<Ordine> allOrdini) {
		this.allOrdini = allOrdini;
	}

	public List<CartaDiCredito> getAllCarteDiCreditoUtente() {
		return allCarteDiCreditoUtente;
	}

	public void setAllCarteDiCreditoUtente(List<CartaDiCredito> allCarteDiCreditoUtente) {
		this.allCarteDiCreditoUtente = allCarteDiCreditoUtente;
	}

	public List<IndirizzoDiSpedizione> getAllIndirizziSpedizioneUtente() {
		return allIndirizziSpedizioneUtente;
	}

	public void setAllIndirizziSpedizioneUtente(List<IndirizzoDiSpedizione> allIndirizziSpedizioneUtente) {
		this.allIndirizziSpedizioneUtente = allIndirizziSpedizioneUtente;
	}

	public Boolean getVisibile() {
		return visibile;
	}

	public void setVisibile(Boolean visibile) {
		this.visibile = visibile;
	}

}
