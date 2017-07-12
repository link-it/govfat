package org.govmix.proxy.pcc.wsclient;

import java.util.List;

import org.govmix.pcc.fatture.ContabilizzazioneTipo;

public class ContabilizzazioneUtilsBean {

	private boolean existsScadenze;
	private boolean contabilizzazionePrevioStorno;
	private boolean stornoSenzaRicontabilizzazione;
	
	public boolean isExistsScadenze() {
		return existsScadenze;
	}
	public void setExistsScadenze(boolean existsScadenze) {
		this.existsScadenze = existsScadenze;
	}
//	public boolean isExistsContabilizzazioni() {
//		return existsContabilizzazioni;
//	}
//	public void setExistsContabilizzazioni(boolean existsContabilizzazioni) {
//		this.existsContabilizzazioni = existsContabilizzazioni;
//	}
	public List<ContabilizzazioneTipo> getContabilizzazioniOriginali() {
		return contabilizzazioniOriginali;
	}
	public void setContabilizzazioniOriginali(
			List<ContabilizzazioneTipo> contabilizzazioniOriginali) {
		this.contabilizzazioniOriginali = contabilizzazioniOriginali;
	}
	public boolean isContabilizzazionePrevioStorno() {
		return contabilizzazionePrevioStorno;
	}
	public void setContabilizzazionePrevioStorno(
			boolean contabilizzazionePrevioStorno) {
		this.contabilizzazionePrevioStorno = contabilizzazionePrevioStorno;
	}
	public boolean isStornoSenzaRicontabilizzazione() {
		return stornoSenzaRicontabilizzazione;
	}
	public void setStornoSenzaRicontabilizzazione(
			boolean stornoSenzaRicontabilizzazione) {
		this.stornoSenzaRicontabilizzazione = stornoSenzaRicontabilizzazione;
	}
//	private boolean existsContabilizzazioni;
	private List<ContabilizzazioneTipo> contabilizzazioniOriginali;

}
