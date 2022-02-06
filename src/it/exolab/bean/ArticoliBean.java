package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.dao.ArticoloDAO;
import it.exolab.dto.Articolo;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ArticoliBean implements Serializable {

	private static final long serialVersionUID = -7173788432598696339L;
	
	static Logger log = Logger.getLogger(ArticoliBean.class);
	
	private List<Articolo> allArticoli;
	private Articolo articoloSelezionato;
	
	@PostConstruct
	public void init() {
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli();
		allArticoli.forEach(art -> log.info(art));
	}
	
    public void clearMultiViewState() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        PrimeFaces.current().multiViewState().clearAll(viewId, true, (clientId) -> {
            showMessage(clientId);
        });
    }
    
    private void showMessage(String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }

	public List<Articolo> getAllArticoli() {
		return allArticoli;
	}

	public void setAllArticoli(List<Articolo> allArticoli) {
		this.allArticoli = allArticoli;
	}

	public Articolo getArticoloSelezionato() {
		return articoloSelezionato;
	}

	public void setArticoloSelezionato(Articolo articoloSelezionato) {
		this.articoloSelezionato = articoloSelezionato;
		log.info("Articolo selezionato : " + articoloSelezionato.toString());
	}

}
