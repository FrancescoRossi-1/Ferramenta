package it.exolab.service;

import java.util.List;

import org.apache.log4j.Logger;

import it.exolab.dto.Articolo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.OgettoEsistente;

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

	public static void checkEquals(Articolo addArticolo, List<Articolo> allArticoli) throws OgettoEsistente {
		if(allArticoli.contains(addArticolo)) {
			throw new OgettoEsistente(addArticolo);
		}
	}

}
