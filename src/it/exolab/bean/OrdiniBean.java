package it.exolab.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import it.exolab.constants.Constants;
import it.exolab.dao.ArticoloDAO;
import it.exolab.dao.CartaDiCreditoDAO;
import it.exolab.dao.DettagliOrdineDAO;
import it.exolab.dao.IndirizzoDiSpedizioneDAO;
import it.exolab.dao.OrdineDAO;
import it.exolab.dto.Articolo;
import it.exolab.dto.CartaDiCredito;
import it.exolab.dto.Indirizzo;
import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.dto.Ordine;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.OggettoEsistente;
import it.exolab.pojo.CartaDiCreditoPOJO;
import it.exolab.pojo.DettagliOrdinePOJO;
import it.exolab.pojo.IndirizzoDiSpedizionePOJO;
import it.exolab.pojo.IndirizzoPOJO;
import it.exolab.pojo.OrdinePOJO;
import it.exolab.pojo.ProvinciaPOJO;
import it.exolab.service.ValidationService;

@SuppressWarnings( "deprecation" )
@ManagedBean
@SessionScoped
public class OrdiniBean implements Serializable {

	private static final long serialVersionUID = -7859175842297874565L;

	static Logger log = LogManager.getLogger(OrdiniBean.class);

	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	@ManagedProperty("#{carrelloBean}")
	private CarrelloBean carrelloBean;

	@ManagedProperty ( "#{indirizziBean}" )
	private IndirizziBean indirizziBean;
	
	private Indirizzo addIndirizzoOrdine;
	private Indirizzo addIndirizzoSpedizione;

	private List<CartaDiCredito> allCarteDiCredito;
	private List<CartaDiCredito> allCarteDiCreditoUtente;
	private Map<CartaDiCredito, Indirizzo> cartaDiCreditoEIndirizzoFatturazione;
	private ArrayList<Map.Entry<CartaDiCredito,Indirizzo>> cartaDiCreditoMap;

	private CartaDiCredito addCartaDiCredito;
	private String meseScadenza;
	private String annoScadenza;
	private List<Integer> mesiScadenza;
	private List<Integer> anniScadenza;

	private CartaDiCreditoPOJO cartaDiCreditoSelezionata;

	private List<IndirizzoDiSpedizione> allIndirizziDiSpedizione;
	private List<IndirizzoDiSpedizione> indirizziSpedizioneUtente;
	private Map<Indirizzo, IndirizzoDiSpedizione> indirizzoEIndirizzoSpedizioneUtente;
	private ArrayList<Map.Entry<Indirizzo,IndirizzoDiSpedizione>> indirizziMap;

	private IndirizzoDiSpedizione addIndirizzoDiSpedizione;
	private IndirizzoDiSpedizionePOJO indirizzoDiSpedizioneSelezionato;

	private List<OrdinePOJO> allOrdini;
	private List<DettagliOrdinePOJO> allDettagliOrdine;

	private Map<OrdinePOJO,List<DettagliOrdinePOJO>> allOrdiniAndDettagli;
	private ArrayList<Map.Entry<OrdinePOJO,List<DettagliOrdinePOJO>>> allOrdiniAndDettagliEntry;

	private Ordine addOrdine;
	private Date dataConsegna; 

	private Boolean ordineEffettuato;
	private String ordineEffettuatoMessage;

	@PostConstruct
	public void init() {

		/*Istanze e inizializzazioni*/
		ordineEffettuato = false;
		ordineEffettuatoMessage = null;

		addOrdine = new Ordine();
		addIndirizzoOrdine = new Indirizzo();
		addIndirizzoSpedizione = new Indirizzo();
		addCartaDiCredito = new CartaDiCredito();
		cartaDiCreditoEIndirizzoFatturazione = new HashMap<>();
		addIndirizzoDiSpedizione = new IndirizzoDiSpedizione();
		indirizzoDiSpedizioneSelezionato = new IndirizzoDiSpedizionePOJO();
		indirizzoEIndirizzoSpedizioneUtente = new HashMap<>();
		cartaDiCreditoSelezionata = new CartaDiCreditoPOJO();
		allOrdiniAndDettagli = new HashMap<>();
		
		allCarteDiCredito = CartaDiCreditoDAO.getInstance().findAllCarte(); //estrazione di tutte le carte di credito
		allCarteDiCreditoUtente = CartaDiCreditoDAO.getInstance().findAllByUserId(sessionBean.getLoggedUser()); //estrazione delle carte di credito dell'utente

		for ( Indirizzo indirizzo : indirizziBean.getAllIndirizzi() ) {
			for (CartaDiCredito cartaDiCredito : allCarteDiCreditoUtente) {
				if(indirizzo.getId_indirizzo().equals(cartaDiCredito.getId_indirizzo_fatturazione())) {
					cartaDiCreditoEIndirizzoFatturazione.put(cartaDiCredito, indirizzo);
				}
			}
		}

		Set<Map.Entry<CartaDiCredito, Indirizzo>> cartaDiCreditoSet = cartaDiCreditoEIndirizzoFatturazione.entrySet();
		cartaDiCreditoMap = new ArrayList<Map.Entry<CartaDiCredito,Indirizzo>>(cartaDiCreditoSet);

		mesiScadenza = IntStream.rangeClosed(1 , 12).boxed().collect(Collectors.toList());

		Integer thisYear = Integer.parseInt(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
		Integer maxYear = thisYear + 10;

		anniScadenza = IntStream.rangeClosed( thisYear , maxYear ).boxed().collect(Collectors.toList());


		/*Estrapola orini e dettagli*/
		allOrdini = OrdineDAO.getInstance().findAllOrdini();
		allDettagliOrdine = DettagliOrdineDAO.getInstance().findAllDettagliOrdini();


		/*Inizializzazione mappa con ordini e dettagli*/
		for (OrdinePOJO ordine : allOrdini) {
			int count = 0;
			for (DettagliOrdinePOJO dettagliOrdine : allDettagliOrdine) {
				if(ordine.getId().equals(dettagliOrdine.getOrdineDiRiferimento().getId_ordine())) {
					if(count == 0) {
						allOrdiniAndDettagli.put(ordine, new ArrayList<>());
					}
					allOrdiniAndDettagli.get(ordine).add(dettagliOrdine);
					count++;
				}
			}
		}
		
		Set<Map.Entry<OrdinePOJO, List<DettagliOrdinePOJO>>> ordiniEDettagliSet = allOrdiniAndDettagli.entrySet();
		allOrdiniAndDettagliEntry = new ArrayList<Map.Entry<OrdinePOJO,List<DettagliOrdinePOJO>>>(ordiniEDettagliSet);

		allIndirizziDiSpedizione = IndirizzoDiSpedizioneDAO.getInstance().findAll(); 
		indirizziSpedizioneUtente = IndirizzoDiSpedizioneDAO.getInstance().findAllByUserId(sessionBean.getLoggedUser());

		/*Riempimento indirizzo e indirizzi spedizione*/
		for ( Indirizzo indirizzo : indirizziBean.getAllIndirizzi()) {
			for (IndirizzoDiSpedizione indirizzoDiSpedizione : allIndirizziDiSpedizione) {
				if(indirizzo.getId_indirizzo().equals(indirizzoDiSpedizione.getId_indirizzo())) {
					indirizzoEIndirizzoSpedizioneUtente.put(indirizzo, indirizzoDiSpedizione);
				}
			}
		}

		Set<Map.Entry<Indirizzo, IndirizzoDiSpedizione>> indirizziSet = indirizzoEIndirizzoSpedizioneUtente.entrySet();
		indirizziMap = new ArrayList<Map.Entry<Indirizzo,IndirizzoDiSpedizione>>(indirizziSet);

		dataConsegna = new Date(System.currentTimeMillis() + Constants.Ordini.FOUR_DAYS_IN_MILLISECONDS);

	} 

	public void insertOrdine() {

		try {

			log.info("Entrato insert");

			/*Controlla se l'utente ha inserito metodo di pagamento e indirizzo spedizione*/
			if(indirizzoDiSpedizioneSelezionato.getId() == null) {
				throw new CampoRichiesto("indirizzo di spedizione");
			}

			if(cartaDiCreditoSelezionata.getId() == null) {
				throw new CampoRichiesto("metodo di pagamento");
			}

			/*crea il modello dell'ordine*/
			addOrdine.setId_indirizzo_spedizione(indirizzoDiSpedizioneSelezionato.getId());
			addOrdine.setId_carta_credito(cartaDiCreditoSelezionata.getId());
			addOrdine.setTotale_ordine(carrelloBean.getCarrelloUtente().getTotale_ordine());
			addOrdine.setId_utente(sessionBean.getLoggedUser().getId());
			addOrdine.setStato(Constants.Ordini.STATI_ORDINE.get(0));
			addOrdine.setData_consegna(dataConsegna);

			/*Inserimento ordine e dettagli*/
			OrdineDAO.getInstance().insertOrdine(addOrdine); 
			DettagliOrdineDAO.getInstance().insertDettagliOrdine(addOrdine,(HashMap<Articolo, Integer>) carrelloBean.getArticoliEQuantita());

			List<Articolo> articoliOrdinati = new ArrayList<>(carrelloBean.getArticoliEQuantita().keySet());

			ArticoloDAO.getInstance().updateQuantitaDisponibileAll(articoliOrdinati,addOrdine);


			carrelloBean.deleteCarrelloFromUserId(); //elimina il carrello dell'utente

			carrelloBean.getArticoliBean().init(); 

			ordineEffettuato = true;
			fillOrdineEffettuatoMessage();

			PrimeFaces.current().ajax().update("menuForm:tabView:tabOrdine");



		} catch ( CampoRichiesto cr ) {

			sessionBean.setErrorMessage(cr.getMessage());

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		}

	}

	private void fillOrdineEffettuatoMessage() {
		String message = "Complimenti " + sessionBean.getLoggedUser().getNome() + " il tuo ordine è stato confermato!\n";

		if(carrelloBean.getArticoliEQuantita().size() > 1) {
			message += "I tuoi articoli saranno recapitati ";
		}else {
			message += "Il tuo articolo sarà recapitato ";
		}

		IndirizzoPOJO indirizzo = indirizzoDiSpedizioneSelezionato.getIndirizzoDiRiferimento();

		message += "presso il seguente indirizzo: " + indirizzo.getVia() + " " + indirizzo.getNCivico() + " , " +  indirizzo.getCap() + ".\n" ;


		message += "La data prevista per la consegna è per il: " + new SimpleDateFormat().format(dataConsegna) + ".\n" ;

		ordineEffettuatoMessage = message;

	}

	public void insertIndirizzoDiSpedizione() {

		try {

			ValidationService.checkParametersIndirizzoDiSpedizione(addIndirizzoDiSpedizione); //validazione
			
			//TODO CHECK BUG NON INSERISCE ID PROVINCIA E CAP

			indirizziBean.setAddIndirizzo(addIndirizzoSpedizione);

			log.info(indirizziBean.getAddIndirizzo().toString());
			Long idIndirizzo = indirizziBean.insertIndirizzo();

			/*Inizializzazione ids*/
			addIndirizzoDiSpedizione.setId_indirizzo(idIndirizzo);
			addIndirizzoDiSpedizione.setId_utente(sessionBean.getLoggedUser().getId());


			/*Controlla se l'indirizzo è già presente*/
			if(indirizziSpedizioneUtente.contains(addIndirizzoDiSpedizione)) {
				throw new OggettoEsistente(addIndirizzoDiSpedizione);
			}

			IndirizzoDiSpedizioneDAO.getInstance().insertIndirizzoDiSpedizione(addIndirizzoDiSpedizione, sessionBean.getLoggedUser());

			init(); 

			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionIndirizziSpedizione:tabIndirizziSpedizione"); //aggiornamento pagina visualizzazzione indirizzi
			sessionBean.setSuccessMessage(Constants.Messages.INSERT_INDIRIZZO_SPEDIZIONE_SUCCESS);

		} catch ( CampoRichiesto cr ) {

			sessionBean.setErrorMessage(cr.getMessage());

		} catch ( FormatoErrato fe ) {

			sessionBean.setErrorMessage(fe.getMessage());

		} catch ( OggettoEsistente oe ) {

			sessionBean.setErrorMessage(oe.getMessage());

		} catch (Exception e) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		}

	}

	public void insertCartaDiCredito () {

		try {

			addCartaDiCredito.setData_scadenza(meseScadenza + "/" + annoScadenza);

			ValidationService.checkParametersCartaDiCredito(addCartaDiCredito);

			addCartaDiCredito.setId_utente(sessionBean.getLoggedUser().getId());

			indirizziBean.setAddIndirizzo(addIndirizzoOrdine);
			Long idIndirizzo = indirizziBean.insertIndirizzo();
			addCartaDiCredito.setId_indirizzo_fatturazione(idIndirizzo);

			if(allCarteDiCreditoUtente.contains(addCartaDiCredito)) {
				throw new OggettoEsistente(addCartaDiCredito);
			}

			CartaDiCreditoDAO.getInstance().insertCartaDiCredito(addCartaDiCredito);

			init();
			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionMetodoPagamento:tabMetodoDiPagamento"); //aggiornamento pagina visualizzazzione metodi di pagamento
			sessionBean.setSuccessMessage(Constants.Messages.INSERT_METODO_PAGAMENTO_SUCCESS);

		} catch ( FormatoErrato fe ) {

			sessionBean.setErrorMessage(fe.getMessage());

		} catch ( CampoRichiesto cr ) {

			sessionBean.setErrorMessage(cr.getMessage());

		} catch ( OggettoEsistente oe ) {

			sessionBean.setErrorMessage(oe.getMessage());

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);

		}

	}

	public void deleteCartaDiCredito ( CartaDiCredito cartaDiCredito ) {

		try {

			CartaDiCreditoDAO.getInstance().deleteCartaDiCreditoFromId(cartaDiCredito);

			sessionBean.setSuccessMessage(Constants.Messages.DELETE_METODO_DI_PAGAMENTO_SUCCESS);

			init();

			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionMetodoPagamento:tabMetodoDiPagamento");

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);

		}

	}

	public void deleteIndirizzoDiSpedizione( IndirizzoDiSpedizione indirizzoDiSpedizione ) {

		try {

			IndirizzoDiSpedizioneDAO.getInstance().deleteIndirizzoDiSpedizioneFromId(indirizzoDiSpedizione);
			init();
			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionOrdine:accorditionIndirizziSpedizione:scrollerIndirizziSpedizione");

			sessionBean.setSuccessMessage(Constants.Messages.DELETE_INDIRIZZO_SPEDIZIONE_SUCCESS);

		} catch ( Exception e ) {

			sessionBean.setErrorMessage(Constants.ExceptionMessages.UNKNOWN_ERROR);
			log.info(e.getMessage(), e);

		}

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public CarrelloBean getCarrelloBean() {
		return carrelloBean;
	}

	public void setCarrelloBean(CarrelloBean carrelloBean) {
		this.carrelloBean = carrelloBean;
	}

	public IndirizziBean getIndirizziBean() {
		return indirizziBean;
	}

	public void setIndirizziBean(IndirizziBean indirizziBean) {
		this.indirizziBean = indirizziBean;
	}

	public List<CartaDiCredito> getAllCarteDiCredito() {
		return allCarteDiCredito;
	}

	public void setAllCarteDiCredito(List<CartaDiCredito> allCarteDiCredito) {
		this.allCarteDiCredito = allCarteDiCredito;
	}

	public List<CartaDiCredito> getAllCarteDiCreditoUtente() {
		return allCarteDiCreditoUtente;
	}

	public void setAllCarteDiCreditoUtente(List<CartaDiCredito> allCarteDiCreditoUtente) {
		this.allCarteDiCreditoUtente = allCarteDiCreditoUtente;
	}

	public Map<CartaDiCredito, Indirizzo> getCartaDiCreditoEIndirizzoFatturazione() {
		return cartaDiCreditoEIndirizzoFatturazione;
	}

	public void setCartaDiCreditoEIndirizzoFatturazione(
			Map<CartaDiCredito, Indirizzo> cartaDiCreditoEIndirizzoFatturazione) {
		this.cartaDiCreditoEIndirizzoFatturazione = cartaDiCreditoEIndirizzoFatturazione;
	}

	public ArrayList<Map.Entry<CartaDiCredito, Indirizzo>> getCartaDiCreditoMap() {
		return cartaDiCreditoMap;
	}

	public void setCartaDiCreditoMap(ArrayList<Map.Entry<CartaDiCredito, Indirizzo>> cartaDiCreditoMap) {
		this.cartaDiCreditoMap = cartaDiCreditoMap;
	}

	public CartaDiCredito getAddCartaDiCredito() {
		return addCartaDiCredito;
	}

	public void setAddCartaDiCredito(CartaDiCredito addCartaDiCredito) {
		this.addCartaDiCredito = addCartaDiCredito;
	}

	public String getMeseScadenza() {
		return meseScadenza;
	}

	public void setMeseScadenza(String meseScadenza) {
		this.meseScadenza = meseScadenza;
	}

	public String getAnnoScadenza() {
		return annoScadenza;
	}

	public void setAnnoScadenza(String annoScadenza) {
		this.annoScadenza = annoScadenza;
	}

	public List<Integer> getMesiScadenza() {
		return mesiScadenza;
	}

	public void setMesiScadenza(List<Integer> mesiScadenza) {
		this.mesiScadenza = mesiScadenza;
	}

	public List<Integer> getAnniScadenza() {
		return anniScadenza;
	}

	public void setAnniScadenza(List<Integer> anniScadenza) {
		this.anniScadenza = anniScadenza;
	}

	public Indirizzo getAddIndirizzoOrdine() {
		return addIndirizzoOrdine;
	}

	public void setAddIndirizzoOrdine(Indirizzo addIndirizzoOrdine) {
		this.addIndirizzoOrdine = addIndirizzoOrdine;
	}

	public Indirizzo getAddIndirizzoSpedizione() {
		return addIndirizzoSpedizione;
	}

	public void setAddIndirizzoSpedizione(Indirizzo addIndirizzoSpedizione) {
		this.addIndirizzoSpedizione = addIndirizzoSpedizione;
	}

	public CartaDiCreditoPOJO getCartaDiCreditoSelezionata() {
		return cartaDiCreditoSelezionata;
	}

	public void setCartaDiCreditoSelezionata(CartaDiCreditoPOJO cartaDiCreditoSelezionata) {
		this.cartaDiCreditoSelezionata = cartaDiCreditoSelezionata;
	}

	public void setCartaDiCreditoSelezionata(CartaDiCredito cartaDiCredito, Indirizzo indirizzo) {


		/*L'utente vuole cambiare indirizzo di spedizione*/
		if(indirizzo == null && cartaDiCredito == null) {
			cartaDiCreditoSelezionata = new CartaDiCreditoPOJO();
		}else {			
			/*Casting manuale da dto a pojo*/
			
			IndirizzoPOJO indirizzoPOJO = new IndirizzoPOJO();
			indirizzoPOJO.setIdIndirizzo(indirizzo.getId_indirizzo());
			indirizzoPOJO.setNCivico(indirizzo.getN_civico());
			indirizzoPOJO.setVia(indirizzo.getVia());
			
			ProvinciaPOJO provinciaPOJO = new ProvinciaPOJO();
			provinciaPOJO.setIdProvincia(indirizzo.getId_provincia());
			
			indirizzoPOJO.setProvinciaAppartenente(provinciaPOJO);

			cartaDiCreditoSelezionata.setIndirizzoFatturazione(indirizzoPOJO);
			cartaDiCreditoSelezionata.setUtenteDiRiferimento(sessionBean.getLoggedUser());
			cartaDiCreditoSelezionata.setId(cartaDiCredito.getId_carte_di_credito());
			cartaDiCreditoSelezionata.setNumeroCarta(cartaDiCredito.getNumero_carta());
			cartaDiCreditoSelezionata.setDataScadenza(cartaDiCredito.getData_scadenza());
			cartaDiCreditoSelezionata.setCVV(cartaDiCredito.getCVV());
			cartaDiCreditoSelezionata.setNominativoProprietario(cartaDiCredito.getNominativo_proprietario());
			cartaDiCreditoSelezionata.setNomeCircuito(cartaDiCredito.getNome_circuito());
		}

	}

	public List<OrdinePOJO> getAllOrdini() {
		return allOrdini;
	}

	public void setAllOrdini(List<OrdinePOJO> allOrdini) {
		this.allOrdini = allOrdini;
	}

	public List<DettagliOrdinePOJO> getAllDettagliOrdine() {
		return allDettagliOrdine;
	}

	public void setAllDettagliOrdine(List<DettagliOrdinePOJO> allDettagliOrdine) {
		this.allDettagliOrdine = allDettagliOrdine;
	}

	public Map<OrdinePOJO, List<DettagliOrdinePOJO>> getAllOrdiniAndDettagli() {
		return allOrdiniAndDettagli;
	}

	public void setAllOrdiniAndDettagli(Map<OrdinePOJO, List<DettagliOrdinePOJO>> allOrdiniAndDettagli) {
		this.allOrdiniAndDettagli = allOrdiniAndDettagli;
	}

	public ArrayList<Map.Entry<OrdinePOJO, List<DettagliOrdinePOJO>>> getAllOrdiniAndDettagliEntry() {
		return allOrdiniAndDettagliEntry;
	}

	public void setAllOrdiniAndDettagliEntry(
			ArrayList<Map.Entry<OrdinePOJO, List<DettagliOrdinePOJO>>> allOrdiniAndDettagliEntry) {
		this.allOrdiniAndDettagliEntry = allOrdiniAndDettagliEntry;
	}

	public Ordine getAddOrdine() {
		return addOrdine;
	}

	public void setAddOrdine(Ordine addOrdine) {
		this.addOrdine = addOrdine;
	}

	public Date getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Date dataOdiernaConsegna) {
		this.dataConsegna = dataOdiernaConsegna;
	}

	public List<IndirizzoDiSpedizione> getAllIndirizziDiSpedizione() {
		return allIndirizziDiSpedizione;
	}


	public void setAllIndirizziDiSpedizione(List<IndirizzoDiSpedizione> allIndirizziDiSpedizione) {
		this.allIndirizziDiSpedizione = allIndirizziDiSpedizione;
	}


	public List<IndirizzoDiSpedizione> getIndirizziSpedizioneUtente() {
		return indirizziSpedizioneUtente;
	}


	public void setIndirizziSpedizioneUtente(List<IndirizzoDiSpedizione> indirizziSpedizioneUtente) {
		this.indirizziSpedizioneUtente = indirizziSpedizioneUtente;
	}


	public Map<Indirizzo, IndirizzoDiSpedizione> getIndirizzoEIndirizzoSpedizioneUtente() {
		return indirizzoEIndirizzoSpedizioneUtente;
	}


	public void setIndirizzoEIndirizzoSpedizioneUtente(
			Map<Indirizzo, IndirizzoDiSpedizione> indirizzoEIndirizzoSpedizioneUtente) {
		this.indirizzoEIndirizzoSpedizioneUtente = indirizzoEIndirizzoSpedizioneUtente;
	}


	public ArrayList<Map.Entry<Indirizzo, IndirizzoDiSpedizione>> getIndirizziMap() {
		return indirizziMap;
	}


	public void setIndirizziMap(ArrayList<Map.Entry<Indirizzo, IndirizzoDiSpedizione>> indirizziMap) {
		this.indirizziMap = indirizziMap;
	}


	public IndirizzoDiSpedizione getAddIndirizzoDiSpedizione() {
		return addIndirizzoDiSpedizione;
	}


	public void setAddIndirizzoDiSpedizione(IndirizzoDiSpedizione addIndirizzoDiSpedizione) {
		this.addIndirizzoDiSpedizione = addIndirizzoDiSpedizione;
	}


	public IndirizzoDiSpedizionePOJO getIndirizzoDiSpedizioneSelezionato() {
		return indirizzoDiSpedizioneSelezionato;
	}


	public void setIndirizzoDiSpedizioneSelezionato(IndirizzoDiSpedizionePOJO indirizzoDiSpedizioneSelezionato) {
		this.indirizzoDiSpedizioneSelezionato = indirizzoDiSpedizioneSelezionato;
	}

	public void setIndirizzoDiSpedizioneSelezionato(Indirizzo indirizzo, IndirizzoDiSpedizione indirizzoDiSpedizione) {
		/*L'utente vuole cambiare indirizzo di spedizione*/
		if(indirizzo == null && indirizzoDiSpedizione == null) {
			indirizzoDiSpedizioneSelezionato = new IndirizzoDiSpedizionePOJO();
		}else {
			/*Casting manuale da dto a pojo*/
			
			IndirizzoPOJO indirizzoPOJO = new IndirizzoPOJO();
			indirizzoPOJO.setIdIndirizzo(indirizzo.getId_indirizzo());
			indirizzoPOJO.setNCivico(indirizzo.getN_civico());
			indirizzoPOJO.setVia(indirizzo.getVia());
			
			ProvinciaPOJO provinciaPOJO = new ProvinciaPOJO();
			provinciaPOJO.setIdProvincia(indirizzo.getId_provincia());
			
			indirizzoPOJO.setProvinciaAppartenente(provinciaPOJO);
			
			indirizzoDiSpedizioneSelezionato.setIndirizzoDiRiferimento(indirizzoPOJO);
			indirizzoDiSpedizioneSelezionato.setUtenteDiRiferimento(sessionBean.getLoggedUser());
			indirizzoDiSpedizioneSelezionato.setId(indirizzoDiSpedizione.getId_indirizzo_spedizione());
			indirizzoDiSpedizioneSelezionato.setScala(indirizzoDiSpedizione.getScala());
			indirizzoDiSpedizioneSelezionato.setInterno(indirizzoDiSpedizione.getInterno());
		}

	}

	public Boolean getOrdineEffettuato() {
		return ordineEffettuato;
	}

	public void setOrdineEffettuato(Boolean ordineEffettuato) {
		this.ordineEffettuato = ordineEffettuato;
	}

	public String getOrdineEffettuatoMessage() {
		return ordineEffettuatoMessage;
	}

	public void setOrdineEffettuatoMessage(String ordineEffettuatoMessage) {
		this.ordineEffettuatoMessage = ordineEffettuatoMessage;
	}

}
