package it.exolab.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.exolab.dao.ProvinciaDAO;
import it.exolab.dto.Provincia;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ProvinceBean implements Serializable {
	
	private static final long serialVersionUID = 162684773251788068L;

	static Logger log = LogManager.getLogger(ProvinceBean.class);

	private List<Provincia> allProvince;
	
	private Long idProvinciaSelezionata;
	
	@PostConstruct
	public void init() {
		
		allProvince = ProvinciaDAO.getInstance().findAll();
		
	}

	public List<Provincia> getAllProvince() {
		return allProvince;
	}

	public void setAllProvince(List<Provincia> allProvince) {
		this.allProvince = allProvince;
	}

	public Long getIdProvinciaSelezionata() {
		return idProvinciaSelezionata;
	}

	public void setIdProvinciaSelezionata(Long idProvinciaSelezionata) {
		this.idProvinciaSelezionata = idProvinciaSelezionata;
		log.info("#### id provincia settato");
	}





	
}
