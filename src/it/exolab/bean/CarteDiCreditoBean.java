package it.exolab.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import it.exolab.constants.Constants;
import it.exolab.dao.CartaDiCreditoDAO;
import it.exolab.dto.CartaDiCredito;
import it.exolab.dto.Indirizzo;
import it.exolab.exception.CampoRichiesto;
import it.exolab.exception.FormatoErrato;
import it.exolab.exception.OggettoEsistente;
import it.exolab.service.ValidationService;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class CarteDiCreditoBean implements Serializable {
	
	static Logger log = Logger.getLogger(CarteDiCreditoBean.class);

	private static final long serialVersionUID = 3207052191953632410L;
	
	@ManagedProperty ( "#{sessionBean}" )
	private SessionBean sessionBean;
	
	@ManagedProperty ( "#{indirizziBean}" )
	private IndirizziBean indirizziBean;
	
	private List<CartaDiCredito> allCarteDiCreditoUtente;
	private Map<CartaDiCredito, Indirizzo> cartaDiCreditoEIndirizzoFatturazione;
	private ArrayList<Map.Entry<CartaDiCredito,Indirizzo>> cartaDiCreditoMap;
	
	private CartaDiCredito addCartaDiCredito;
	private String meseScadenza;
	private String annoScadenza;
	private List<Integer> mesiScadenza;
	private List<Integer> anniScadenza;
	
	private CartaDiCredito cartaDiCreditoSelezionata;
	
	@PostConstruct
	public void init() {
		
		log.info("INIT CARTA DI CREDITO");
		
		addCartaDiCredito = new CartaDiCredito();
		cartaDiCreditoEIndirizzoFatturazione = new HashMap<>();
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

	}
	
	public void insertCartaDiCredito () {
		
		try {
			
			addCartaDiCredito.setData_scadenza(meseScadenza + "/" + annoScadenza);
			
			ValidationService.checkParametersCartaDiCredito(addCartaDiCredito);
			
			addCartaDiCredito.setId_utente(sessionBean.getLoggedUser().getId());
			
			Long idIndirizzo = indirizziBean.insertIndirizzo();
			addCartaDiCredito.setId_indirizzo_fatturazione(idIndirizzo);
			
			if(allCarteDiCreditoUtente.contains(addCartaDiCredito)) {
				throw new OggettoEsistente(addCartaDiCredito);
			}
			
			CartaDiCreditoDAO.getInstance().insertCartaDiCredito(addCartaDiCredito);
			
			PrimeFaces.current().ajax().update("menuForm:tabView:accorditionMetodoPagamento:scrollerMetodoDiPagamento"); //aggiornamento pagina visualizzazzione metodi di pagamento
			init();
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
		
		
	}
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public IndirizziBean getIndirizziBean() {
		return indirizziBean;
	}

	public void setIndirizziBean(IndirizziBean indirizziBean) {
		this.indirizziBean = indirizziBean;
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

	public CartaDiCredito getCartaDiCreditoSelezionata() {
		return cartaDiCreditoSelezionata;
	}

	public void setCartaDiCreditoSelezionata(CartaDiCredito cartaDiCreditoSelezionata) {
		this.cartaDiCreditoSelezionata = cartaDiCreditoSelezionata;
	}
	
	

}
