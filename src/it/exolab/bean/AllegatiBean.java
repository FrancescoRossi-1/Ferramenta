package it.exolab.bean;

import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import it.exolab.dao.AllegatoDAO;
import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class AllegatiBean implements Serializable {

	static Logger log = LogManager.getLogger(AllegatiBean.class);
	
	private static final long serialVersionUID = -6254618809475372643L;

	private List<Allegato> allAllegati;
	private Map<Allegato,String> allegatiAndStringConvert;
	private UploadedFiles immagini;

	@PostConstruct
	public void init() {
		
		allegatiAndStringConvert = new HashMap<>();
		
		allAllegati = AllegatoDAO.getInstance().selectAllAllegati();

		for (Allegato allegato : allAllegati) {
			allegatiAndStringConvert.put( allegato , fromImageToString(allegato) );
		}
		
		allegatiAndStringConvert.forEach( ( alleg, str ) -> log.info("Allegato : " + alleg.toString() + "Convertito: "  + str));

		System.out.println("Uscito");
		
	}

	public List<Allegato> createListAllegati(Articolo articolo) throws Exception {

		List<UploadedFile> allImages = immagini.getFiles();
		
		List<Allegato> allegatiArticolo = new ArrayList<>();

		for (UploadedFile image : allImages) {

			Allegato allegato = new Allegato();
			
			String nomeFile = image.getFileName();
			String estensioneFile = nomeFile.substring(nomeFile.length() - 3);

			byte[] content = new byte[image.getContent().length];
			System.arraycopy(image.getContent(),0,content,0,image.getContent().length);

		    allegato.setNome_file(nomeFile);
			allegato.setEstensione(estensioneFile);
			allegato.setContent(content);
			allegato.setId_articolo(articolo.getId_articolo());
			
			allegatiArticolo.add(allegato);
			
		}
		
		return allegatiArticolo;

	}

	public String fromImageToString ( Allegato allegato ) {
		String photoString = new String(Base64.encodeBase64(allegato.getContent()));
		return photoString;
	}

	public List<Allegato> getAllAllegati() {
		return allAllegati;
	}

	public void setAllAllegati(List<Allegato> allAllegati) {
		this.allAllegati = allAllegati;
	}

	public Map<Allegato, String> getAllegatiAndStringConvert() {
		return allegatiAndStringConvert;
	}

	public void setAllegatiAndStringConvert(Map<Allegato, String> allegatiAndStringConvert) {
		this.allegatiAndStringConvert = allegatiAndStringConvert;
	}

	public UploadedFiles getImmagini() {
		return immagini;
	}

	public void setImmagini(UploadedFiles immagini) {
		this.immagini = immagini;
	}
}
