package it.exolab.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import it.exolab.dao.CategoriaDAO;
import it.exolab.dto.Categoria;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class CategorieBean {
	
	static Logger log = Logger.getLogger(CategorieBean.class);
	
	List<Categoria> allCategorie;
	
	@PostConstruct
	public void init() {

		allCategorie = CategoriaDAO.getInstance().selectAllCategorie();
		allCategorie.forEach(cat -> log.info(cat));
	}
	
	public void refreshCategorie() {
		
		//estrapola tutte le categorie
		allCategorie = CategoriaDAO.getInstance().selectAllCategorie();
		log.info("Categorie aggiornate.");
		
	}

	public List<Categoria> getAllCategorie() {
		return allCategorie;
	}

	public void setAllCategorie(List<Categoria> allCategorie) {
		this.allCategorie = allCategorie;
	}

}
