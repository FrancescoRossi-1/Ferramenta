package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import it.exolab.dao.IndirizzoDiSpedizioneDAO;
import it.exolab.dto.IndirizzoDiSpedizione;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class IndirizziDiSpedizioneBean implements Serializable {

	private static final long serialVersionUID = -5987437588436053809L;
	
	private List<IndirizzoDiSpedizione> allIndirizziDiSpedizione;
	
	@PostConstruct
	public void init() {
		
		allIndirizziDiSpedizione = IndirizzoDiSpedizioneDAO.getInstance().findAll();
		
	}

	public List<IndirizzoDiSpedizione> getAllIndirizziDiSpedizione() {
		return allIndirizziDiSpedizione;
	}

	public void setAllIndirizziDiSpedizione(List<IndirizzoDiSpedizione> allIndirizziDiSpedizione) {
		this.allIndirizziDiSpedizione = allIndirizziDiSpedizione;
	}

}
