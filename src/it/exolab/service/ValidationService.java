package it.exolab.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import it.exolab.constants.Constants;
import it.exolab.dao.IndirizzoDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.Categoria;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.GenericCarrelloException;
import it.exolab.exception.GenericFileException;
import it.exolab.exception.OggettoEsistente;
import it.exolab.exception.UtenteNonEsistente;

public class ValidationService {

	static Logger log = Logger.getLogger(ValidationService.class);
	
	public static void checkParametersSignUp(Utente user) throws FormatoErrato, CampoRichiesto {

		if(user.getNome().equals("")) {
			throw new CampoRichiesto("nome");
		}
		
		if(user.getNome().matches(Constants.Regex.CHECK_NOT_NUMBERS)) {
			throw new FormatoErrato("nome");
		}
		
		if(user.getCognome().equals("")) {
			throw new CampoRichiesto("cognome");
		}

		if(user.getCognome().matches(Constants.Regex.CHECK_NOT_NUMBERS)) {
			throw new FormatoErrato("cognome");
		}
		
		if(user.getEmail().equals("") ) {
			throw new CampoRichiesto("email");
		}

		if(!user.getEmail().matches(Constants.Regex.CHECK_EMAIL)) {
			throw new FormatoErrato("email");
		}
		
		if(user.getPassword().equals("") ) {
			throw new CampoRichiesto("password");
		}

		if(user.getPassword().matches(Constants.Regex.CHECK_PASSWORD)) {
			throw new FormatoErrato("password");
		}
		
		if( user.getData_nascita() == null ) {
			throw new CampoRichiesto("data di nascita");
		}

		if(user.getData_nascita().getTime() > new Date().getTime()) {
			throw new FormatoErrato("data nascita");
		}
		
		if(user.getCodice_fiscale().equals("") ) {
			throw new CampoRichiesto("codice fiscale");
		}
		
		if(!user.getCodice_fiscale().matches(Constants.Regex.CHECK_CODICE_FISCALE)) {
			throw new FormatoErrato("codice fiscale");
		}
		
		if(user.getIndirizzoResidenza().getProvinciaDiAppartenenza().getId_province() == -1 ) {
			throw new CampoRichiesto("provincia");
		}
		
		if(user.getIndirizzoResidenza().getVia().equals("") ) {
			throw new CampoRichiesto("via");
		}

		if(user.getIndirizzoResidenza().getVia().matches(Constants.Regex.CHECK_NOT_NUMBERS)) {
			throw new FormatoErrato("via");
		}
		
		if(user.getIndirizzoResidenza().getN_civico().equals("") ) {
			throw new CampoRichiesto("numero civico");
		}

		if(user.getIndirizzoResidenza().getN_civico().matches(Constants.Regex.CHECK_NOT_LETTERS)) {
			throw new FormatoErrato("numero civico");
		}
		
		if(user.getIndirizzoResidenza().getCap().equals("") ) {
			throw new CampoRichiesto("cap");
		}

		if(user.getIndirizzoResidenza().getCap().matches(Constants.Regex.CHECK_NOT_LETTERS)) {
			throw new FormatoErrato("cap");
		}

	}
	
	public static void checkExistingUserSignUp(Utente user) throws OggettoEsistente {
		
		Utente extrapolatedUsers = UtenteDAO.getInstance().selectUserFromEmail(user);

		if( extrapolatedUsers != null ) {
			throw new OggettoEsistente(extrapolatedUsers);
		}
		
	}
	
	public static boolean checkIndirizzoEsistente(Utente user) {
		Indirizzo indirizzoEstrapolato = IndirizzoDAO.getInstance().selectIndirizzoForEquals(user.getIndirizzoResidenza()); //indirizzo comprensivo di provincia
		log.info("Indirizzo esistente? -> " + indirizzoEstrapolato);
		
		if(indirizzoEstrapolato == null) {
			return true;	
		}else  {
			user.setIndirizzoResidenza(indirizzoEstrapolato);
			return false;
		}
	}
	
	public static void checkParametersLogin(Utente loginUser) throws CampoRichiesto {
		
		if(loginUser.getEmail().equals("")) {
			throw new CampoRichiesto("email");
		}
		
		if(loginUser.getPassword().equals("")) {
			throw new CampoRichiesto("password");
		}
		
	}
	
	public static void checkExistingUserLogin(Utente loginUser) throws UtenteNonEsistente {
		Utente extrapolatedUser = UtenteDAO.getInstance().selectUserFromEmailAndPassword(loginUser);
		
		if(extrapolatedUser == null) {
			throw new UtenteNonEsistente();
		}
	}
	
	public static void checkParametersArticolo(Articolo articolo) throws CampoRichiesto {

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
	
	public static void checkEqualsArticolo(Articolo addArticolo, List<Articolo> allArticoli) throws OggettoEsistente {
		if(allArticoli.contains(addArticolo)) {
			throw new OggettoEsistente(addArticolo);
		}
	}
	
	public static void checkImagesAllegati(UploadedFiles articoloImages) throws FileImmagineNonSupportato, GenericFileException, Exception {

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
	
	public static void checkParametersCategoria( Categoria categoria ) throws CampoRichiesto {
		
		if(categoria.getNome_categoria().isEmpty()) {
			throw new CampoRichiesto("nome categoria");
		}
		
		if( categoria.getDescrizione_categoria().isEmpty() ) {
			throw new CampoRichiesto("descrizione categoria");
		}
		
	}
	
	public static void checkExistingCategoria(Categoria categoria, List<Categoria> allCategorie ) throws OggettoEsistente {
		
		for (Categoria cat : allCategorie) {
			if(cat.equals(categoria)) {
				throw new OggettoEsistente(categoria);
			}
		}
		
	}

	public static void checkQuantitaArticolo(Articolo articoloDaAggiungere, Integer quantita) throws GenericCarrelloException {
		
		if( quantita < 1 ) {
			throw new GenericCarrelloException(Constants.ExceptionMessages.QUANTITA_ARTICOLI_MINORE);
		}
		
		if(quantita > articoloDaAggiungere.getQuantita_disponibile()) {
			throw new GenericCarrelloException(Constants.ExceptionMessages.QUANTITA_ARTICOLI_MAGGIORE);
		}
		
	}
	
}
