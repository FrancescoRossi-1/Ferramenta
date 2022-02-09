package it.exolab.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import it.exolab.constants.Constants;
import it.exolab.dao.AllegatoDAO;
import it.exolab.dao.ArticoloDAO;
import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.GenericFileException;
import it.exolab.exception.OggettoEsistente;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ArticoliBean implements Serializable {

	private static final long serialVersionUID = -7173788432598696339L;
	
	static Logger log = Logger.getLogger(ArticoliBean.class);
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	private List<Articolo> allArticoli;
	
	private List<String> stringPhotos;
	private Articolo articoloSelezionato;
	private Articolo addArticolo;
	private UploadedFiles articoloImages;
	
	@PostConstruct
	public void init() {
		
		addArticolo = new Articolo();
		
		allArticoli = ArticoloDAO.getInstance().selectAllArticoli();
		for (Articolo articolo : allArticoli) {
			List<Allegato> allAllegati = AllegatoDAO.getInstance().selectAllAllegatiFromIdArticolo(articolo);
			allAllegati.forEach(allegato -> log.info(allegato.toString()));
			articolo.setAllegatiAppartenenti(allAllegati);
		}
		
		allArticoli.forEach(art -> log.info(art));
		
	}
	
	public void insertArticolo() {
		log.info("--> Inserimento articolo.");

		try {
			
			ValidationService.checkParametersArticolo(getAddArticolo());
			ValidationService.checkEqualsArticolo(getAddArticolo(), getAllArticoli());
			
			ValidationService.checkImagesAllegati(getArticoloImages());
			
			ArticoloDAO.getInstance().insertArticolo(getAddArticolo());
			fillImageList(getAddArticolo(),getArticoloImages());	
			AllegatoDAO.insertAll(getAddArticolo().getAllegatiAppartenenti());
			
			init();
			sessionBean.setSuccessMessage(Constants.Messages.SUCCESFULLY_INSTERTED_PRODUCT);		
			
		} catch ( CampoRichiesto cr ) {
			sessionBean.setErrorMessage(cr.getMessage());
		} catch ( OggettoEsistente oe ) {
			sessionBean.setErrorMessage(oe.getMessage());
		} catch ( FileImmagineNonSupportato fins ) {
			sessionBean.setErrorMessage(fins.getMessage());
		} catch ( GenericFileException gfe ) {
			sessionBean.setErrorMessage(gfe.getMessage());
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(),e);
		}

	}
	
	public void deleteArticolo(Long idArticolo) {
		
		try {
			
			ArticoloDAO.getInstance().deleteArticoloFromId(idArticolo);
			init();
			sessionBean.setSuccessMessage(Constants.Messages.DELETE_ARTICOLO_SUCCESS);
			PrimeFaces.current().ajax().update("menuForm:tabView:menuAreaRiservata:gestioneArticoli");
			
		} catch ( Exception e ) {
			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
		}
		
	}
	


	public void fillImageList(Articolo addArticolo, UploadedFiles articoloImages) throws Exception {

		List<UploadedFile> allImages = articoloImages.getFiles();

		for (UploadedFile image : allImages) {
			
			Allegato allegato = new Allegato();
			
			String nomeFile = image.getFileName();
			String estensioneFile = nomeFile.substring(nomeFile.length() - 3);
			
			byte[] content = new byte[image.getContent().length];
			System.arraycopy(image.getContent(),0,content,0,image.getContent().length);
			
			allegato.setNome_file(nomeFile);
			allegato.setEstensione(estensioneFile);
			allegato.setContent(content);
			allegato.setId_articolo(addArticolo.getId_articolo());
			
			addArticolo.getAllegatiAppartenenti().add(allegato);
		}

	}
	
	public String fromImageToString ( Articolo articolo, int imageIndex) {
		
		String imageString = "";
		
		for (Articolo art : allArticoli) {
			if(art.equals(articolo)) {
				imageString = new String(Base64.encodeBase64(articolo.getAllegatiAppartenenti().get(imageIndex).getContent()));
			}
		}
		
		return imageString;
		
	}
	
	public List<String> getListOfStringPhotos( Articolo articolo ) {
		
		List<String> imageStringList = new ArrayList<>();
		
		for (Articolo art : allArticoli) {
			if(art.equals(articolo)) {
				for (Allegato allegato : articolo.getAllegatiAppartenenti()) {
					imageStringList.add(new String(Base64.encodeBase64(allegato.getContent())));
				}
			}
		}
		
		return imageStringList;
		
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<Articolo> getAllArticoli() {
		return allArticoli;
	}

	public void setAllArticoli(List<Articolo> allArticoli) {
		this.allArticoli = allArticoli;
	}

	public List<String> getStringPhotos() {
		return stringPhotos;
	}

	public void setStringPhotos(List<String> stringPhotos) {
		this.stringPhotos = stringPhotos;
	}

	public Articolo getArticoloSelezionato() {
		return articoloSelezionato;
	}

	public void setArticoloSelezionato(Articolo articoloSelezionato) {
		stringPhotos = getListOfStringPhotos(articoloSelezionato); 
		this.articoloSelezionato = articoloSelezionato;
		log.info("Articolo selezionato : " + articoloSelezionato.toString());
	}

	public Articolo getAddArticolo() {
		return addArticolo;
	}

	public void setAddArticolo(Articolo addArticolo) {
		this.addArticolo = addArticolo;
	}

	public UploadedFiles getArticoloImages() {
		return articoloImages;
	}

	public void setArticoloImages(UploadedFiles articoloImages) {
		this.articoloImages = articoloImages;
	}

}
