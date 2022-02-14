package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.constants.Constants;
import it.exolab.dao.IndirizzoDAO;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.Utente;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class IndirizziBean implements Serializable {

	static Logger log = Logger.getLogger(IndirizziBean.class);
	
	private static final long serialVersionUID = 6282344715354864997L;
	
	@ManagedProperty ( "#{sessionBean}" )
	private SessionBean sessionBean;
	
	
	@ManagedProperty ( "#{provinceBean}" )
	private ProvinceBean provinceBean;
	
	private List<Indirizzo> allIndirizzi;
	private Indirizzo addIndirizzo;
	
	@PostConstruct
	public void init() {
		
		log.info("Indirizzi init");
		
		addIndirizzo = new Indirizzo();
		
		allIndirizzi = IndirizzoDAO.getInstance().findAll();
		
	}


	public Long insertIndirizzo(Utente utente) {
		
		Long id_indirizzo = null;
		
		try {
			
			addIndirizzo.setId_provincia(provinceBean.getIdProvinciaSelezionata());
			
			if(allIndirizzi.contains(addIndirizzo)) { //indirizzo gi� esistente
				for (Indirizzo indirizzo : allIndirizzi) {
					if(indirizzo.equals(addIndirizzo)) {
						id_indirizzo = indirizzo.getId_indirizzo();
					}
				}
			}else {
				addIndirizzo.setId_provincia(provinceBean.getIdProvinciaSelezionata());
				IndirizzoDAO.getInstance().insertAddress(addIndirizzo,provinceBean.getIdProvinciaSelezionata());
				id_indirizzo = addIndirizzo.getId_indirizzo();
				
			}
			
		} catch ( Exception e ) {
			
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);
			
		}
		
		return id_indirizzo;
		
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	public ProvinceBean getProvinceBean() {
		return provinceBean;
	}


	public void setProvinceBean(ProvinceBean provinceBean) {
		this.provinceBean = provinceBean;
	}


	public List<Indirizzo> getAllIndirizzi() {
		return allIndirizzi;
	}

	public void setAllIndirizzi(List<Indirizzo> allIndirizzi) {
		this.allIndirizzi = allIndirizzi;
	}


	public Indirizzo getAddIndirizzo() {
		return addIndirizzo;
	}


	public void setAddIndirizzo(Indirizzo addIndirizzo) {
		this.addIndirizzo = addIndirizzo;
	}
	
	

}