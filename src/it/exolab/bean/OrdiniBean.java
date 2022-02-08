package it.exolab.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import it.exolab.dto.Ordine;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class OrdiniBean {
	
	List<Ordine> allOrdini;
	
	@PostConstruct
	public void init() {
		 //TODO SELECT ORDINI
	}

}
