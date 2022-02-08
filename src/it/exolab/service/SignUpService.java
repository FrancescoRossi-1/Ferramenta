package it.exolab.service;

import java.util.Date;

import org.apache.log4j.Logger;

import it.exolab.constants.Constants;
import it.exolab.dao.IndirizzoDAO;
import it.exolab.dao.UtenteDAO;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.Utente;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.OgettoEsistente;

public class SignUpService {
	
	static Logger log = Logger.getLogger(SignUpService.class);

	public static void checkParameters(Utente user) throws FormatoErrato, CampoRichiesto {

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

	public static void setUtilityParameters(Utente user) {
		user.setData_iscrizione(new Date(System.currentTimeMillis()));
		user.setIsAdmin(false);
	}

	public static void insertUser(Utente user) throws OgettoEsistente {
		
		Utente extrapolatedUser = UtenteDAO.getInstance().selectUserFromEmail(user);

		if( extrapolatedUser != null ) {
			throw new OgettoEsistente(extrapolatedUser);
		}

		Indirizzo indirizzoEstrapolato = IndirizzoDAO.getInstance().selectIndirizzoForEquals(user.getIndirizzoResidenza()); //indirizzo comprensivo di provincia
		log.info("Indirizzo esistente? -> " + indirizzoEstrapolato);

		if(indirizzoEstrapolato == null) {		
			IndirizzoDAO.getInstance().insertAddress(user.getIndirizzoResidenza());
		}else if( indirizzoEstrapolato.equals(user.getIndirizzoResidenza()) ) {
			user.setIndirizzoResidenza(indirizzoEstrapolato);
		}

		UtenteDAO.getInstance().insertUser(user);

	}

}
