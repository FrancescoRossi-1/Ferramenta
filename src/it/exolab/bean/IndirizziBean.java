package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.exolab.dao.IndirizzoDAO;
import it.exolab.dto.Indirizzo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class IndirizziBean implements Serializable {

	static Logger log = LogManager.getLogger(IndirizziBean.class);

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


	public Long insertIndirizzo() throws CampoRichiesto, FormatoErrato, Exception {

		Long id_indirizzo = null;

		ValidationService.checkParametersIndirizzo(addIndirizzo);
		
		
		if(allIndirizzi.contains(addIndirizzo)) { //indirizzo già esistente
			for (Indirizzo indirizzo : allIndirizzi) {
				if(indirizzo.equals(addIndirizzo)) {
					id_indirizzo = indirizzo.getId_indirizzo();
				}
			}
		}else {
			if(addIndirizzo.getId_provincia() == null) {
				addIndirizzo.setId_provincia(provinceBean.getIdProvinciaSelezionata());
			}
			IndirizzoDAO.getInstance().insertAddress(addIndirizzo);
			id_indirizzo = addIndirizzo.getId_indirizzo();
			
		}
		
		init(); //ricarica gli indirizzi

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
