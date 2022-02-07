package it.exolab.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import it.exolab.constants.Constants;
import it.exolab.dto.Articolo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.GenericFileException;

public class ArticoliService {

	static Logger log = Logger.getLogger(ArticoliService.class);

	public static void checkParameters(Articolo articolo) throws CampoRichiesto {

		if( articolo.getTitolo_articolo().isEmpty() ) {
			throw new CampoRichiesto( "titolo articolo" );
		}

		if( articolo.getDescrizione_articolo().isEmpty() ) {
			throw new CampoRichiesto( "descrizione articolo" );
		}

		if( articolo.getQuantita_disponibile() == null ) {
			throw new CampoRichiesto( "quantità disponibile" );
		}

		if( articolo.getPrezzo_unitario() == null ) {
			throw new CampoRichiesto( "prezzo" );
		}

		if( articolo.getMarchio().isEmpty() ) {
			throw new CampoRichiesto( "marchio" );
		}

		if( articolo.getColore().isEmpty() ) {
			throw new CampoRichiesto( "colore" );
		}

	}

	public static void checkImage(UploadedFile articoloImage) throws FileImmagineNonSupportato, GenericFileException, Exception {

		if(articoloImage.getSize() > 0) {

			log.info("nome immagine->" + articoloImage.getFileName());

			String nomeFile = articoloImage.getFileName();
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

			if(articoloImage.getSize() > Constants.File.MAX_SUPPORTED_DIMENSION) {
				throw new GenericFileException(Constants.ExceptionMessages.FILE_TOO_BIG);
			}

			log.info("Path -> " + Constants.Paths.PRODUCT_IMAGES_PATH);

			try {
				List<File> files = Files.list(Paths.get(Constants.Paths.PRODUCT_IMAGES_PATH))
						.map(Path::toFile)
						.collect(Collectors.toList());

				for (File file : files) {
					if(file.getName().equals(nomeFile)) {
						//TODO controlla se il file viene nominato da solo se ne esiste uno dello stesso nome
					}
				}
			} catch (IOException e) {
				throw new Exception();
			}
		}



	}

	public static void storeFile(UploadedFile articoloImage) {
		// TODO Auto-generated method stub
		
	}


}
