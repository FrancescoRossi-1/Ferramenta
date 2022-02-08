package it.exolab.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import it.exolab.constants.Constants;
import it.exolab.dto.Allegato;
import it.exolab.dto.Articolo;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.GenericFileException;

public class AllegatiService {
	
	static Logger log = Logger.getLogger(AllegatiService.class);
	

	public static void checkImages(UploadedFiles articoloImages) throws FileImmagineNonSupportato, GenericFileException, Exception {

		List<UploadedFile> imagesList = articoloImages.getFiles();

		for (UploadedFile image : imagesList) {

			if(articoloImages.getSize() > 0) {

				log.info("nome immagine->" + image.getFileName());

				String nomeFile = image.getFileName();
				String estensioneFile = nomeFile.substring(nomeFile.length() - 3);
				Boolean flagEstensione = false;

				for( int i = 0; i < Constants.File.SUPPORTED_IMAGE_EXTENSIONS.length && !flagEstensione; i++ ) {
					if(estensioneFile.equals(Constants.File.SUPPORTED_IMAGE_EXTENSIONS[i])) {
						flagEstensione = true;
					}
				}

				if(!flagEstensione) {
					throw new FileImmagineNonSupportato();
				}

				if(image.getSize() > Constants.File.MAX_SUPPORTED_DIMENSION) {
					throw new GenericFileException(Constants.ExceptionMessages.FILE_TOO_BIG);
				}

			}
		}

	}

	public static void fillImageList(Articolo addArticolo, UploadedFiles articoloImages) throws Exception {

		List<UploadedFile> allImages = articoloImages.getFiles();

		for (UploadedFile image : allImages) {
			
			Allegato allegato = new Allegato();
			
			String nomeFile = image.getFileName();
			String estensioneFile = nomeFile.substring(nomeFile.length() - 3);
			
			byte[] content = new byte[image.getContent().length];
			System.arraycopy(image.getContent(),0,content,0,image.getContent().length);

			
			log.info( "Content   -------->> " + content );
			
			allegato.setNome_file(nomeFile);
			allegato.setEstensione(estensioneFile);
			allegato.setContent(content);
			allegato.setId_articolo(addArticolo.getId_articolo());
			
			addArticolo.getAllegatiAppartenenti().add(allegato);
		}

	}

}
