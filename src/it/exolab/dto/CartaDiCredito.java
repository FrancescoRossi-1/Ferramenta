package it.exolab.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartaDiCredito {

	private Long id_carte_di_credito;
	private String numero_carta;
	private String data_scadenza;
	private String CVV;
	private String nominativo_proprietario;
	private String nome_circuito;
	private Long id_utente;
	private Long id_indirizzo_fatturazione;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartaDiCredito other = (CartaDiCredito) obj;
		if(numero_carta.equals(other.numero_carta)) {
			if(data_scadenza.equals(other.data_scadenza)) {
				if(CVV.equals(other.CVV)) {
					if(nome_circuito.equalsIgnoreCase(other.nome_circuito)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CVV == null) ? 0 : CVV.hashCode());
		result = prime * result + ((data_scadenza == null) ? 0 : data_scadenza.hashCode());
		result = prime * result + ((id_carte_di_credito == null) ? 0 : id_carte_di_credito.hashCode());
		result = prime * result + ((id_indirizzo_fatturazione == null) ? 0 : id_indirizzo_fatturazione.hashCode());
		result = prime * result + ((id_utente == null) ? 0 : id_utente.hashCode());
		result = prime * result + ((nome_circuito == null) ? 0 : nome_circuito.hashCode());
		result = prime * result + ((nominativo_proprietario == null) ? 0 : nominativo_proprietario.hashCode());
		result = prime * result + ((numero_carta == null) ? 0 : numero_carta.hashCode());
		return result;
	}



}
