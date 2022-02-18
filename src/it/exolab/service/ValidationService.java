package it.exolab.service;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import it.exolab.bean.UtentiBean;
import it.exolab.constants.Constants;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.CartaDiCredito;
import it.exolab.dto.Categoria;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FileImmagineNonSupportato;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.GenericCarrelloException;
import it.exolab.exception.GenericFileException;
import it.exolab.exception.OggettoEsistente;
import it.exolab.exception.UtenteNonEsistente;
import it.exolab.pojo.UtentePOJO;

public class ValidationService {

	static Logger log = LogManager.getLogger(ValidationService.class);

	@SuppressWarnings("deprecation")
	@ManagedProperty ( "#{utentiBean}" )
	private UtentiBean utentiBean;

	public static void checkParametersSignUp(Utente user, Indirizzo indirizzo, Long idProvincia) throws FormatoErrato, CampoRichiesto {

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

		if(user.getData_nascita() == null ) {
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

		if(idProvincia == -1 ) {
			throw new CampoRichiesto("provincia");
		}
		
		checkParametersIndirizzo(indirizzo);

	}

	public static void checkParametersIndirizzo( Indirizzo indirizzo ) throws CampoRichiesto, FormatoErrato {

		if(indirizzo.getVia().equals("") ) {
			throw new CampoRichiesto("via");
		}

		if(indirizzo.getVia().matches(Constants.Regex.CHECK_NOT_NUMBERS)) {
			throw new FormatoErrato("via");
		}

		if(indirizzo.getN_civico().equals("") ) {
			throw new CampoRichiesto("numero civico");
		}

		if(indirizzo.getN_civico().matches(Constants.Regex.CHECK_NOT_LETTERS)) {
			throw new FormatoErrato("numero civico");
		}

		if(indirizzo.getCap().equals("") ) {
			throw new CampoRichiesto("cap");
		}

		if(indirizzo.getCap().matches(Constants.Regex.CHECK_NOT_LETTERS)) {
			throw new FormatoErrato("cap");
		}

	}

	public static void checkExistingUserSignUp(List<UtentePOJO> allUtenti, Utente user) throws OggettoEsistente {

		for (UtentePOJO utente : allUtenti) {
			if(utente.getEmail().equals(user.getEmail())) {
				throw new OggettoEsistente(user);
			}
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

	public static void checkParametersIndirizzoDiSpedizione(IndirizzoDiSpedizione indirizzoDiSpedizione) throws FormatoErrato, CampoRichiesto {
		
		if( indirizzoDiSpedizione.getScala().length() > 1 ) {
			throw new FormatoErrato("scala");
		}
		
		if( indirizzoDiSpedizione.getInterno().isEmpty() ) {
			throw new CampoRichiesto("interno");
		}
		
		if( indirizzoDiSpedizione.getInterno().length() > 5 ) {
			throw new FormatoErrato("interno");
		}
		
	}

	public static void checkParametersCartaDiCredito(CartaDiCredito cartaDiCredito) throws CampoRichiesto, FormatoErrato {
		
		if( cartaDiCredito.getNumero_carta().isEmpty()) {
			throw new CampoRichiesto("numero carta di credito");
		}
		
		if( !cartaDiCredito.getNumero_carta().matches(Constants.Regex.CHECK_NUMERO_CARTA) ) {
			throw new FormatoErrato("numero carta di credito");
		}
		
		if( cartaDiCredito.getData_scadenza().startsWith("-1") ) {
			throw new FormatoErrato("data scadenza");
		}
		
		if( cartaDiCredito.getCVV().isEmpty() ) {
			throw new CampoRichiesto("cvv");
		}
		
		if( cartaDiCredito.getCVV().length() > 3 ) {
			throw new FormatoErrato("cvv");
		}
		
		if( cartaDiCredito.getNominativo_proprietario().isEmpty() ) {
			throw new CampoRichiesto("intestatario");
		}
		
		if( cartaDiCredito.getNominativo_proprietario().matches(Constants.Regex.CHECK_NOT_NUMBERS) ) {
			throw new FormatoErrato("intestatario");
		}
		
		if( cartaDiCredito.getNome_circuito().isEmpty() ) {
			throw new CampoRichiesto("circuito");
		}
		
		if( !Constants.Ordini.CIRCUITI_ACCETTATI.contains(cartaDiCredito.getNome_circuito().toUpperCase()) ) {
			throw new FormatoErrato("circuito");
		}
		
	}

}
