package it.exolab.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import it.exolab.dao.ProvinciaDAO;
import it.exolab.dto.Provincia;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ProvinceBean {

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
	}



	
}
