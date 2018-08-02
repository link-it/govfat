package org.govmix.fatturapa.parer.beans;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;

public class FatturaBean {

	private FatturaElettronica fattura;
	private List<NotificaEsitoCommittente> esitoCommittente;
	private NotificaDecorrenzaTermini decorrenzaTermini;
	private List<AllegatoFattura> allegati;
	public List<NotificaEsitoCommittente> getEsitoCommittente() {
		return esitoCommittente;
	}
	public void setEsitoCommittente(List<NotificaEsitoCommittente> esitoCommittente) {
		this.esitoCommittente = esitoCommittente;
	}
	public NotificaDecorrenzaTermini getDecorrenzaTermini() {
		return decorrenzaTermini;
	}
	public void setDecorrenzaTermini(NotificaDecorrenzaTermini decorrenzaTermini) {
		this.decorrenzaTermini = decorrenzaTermini;
	}
	public FatturaElettronica getFattura() {
		return fattura;
	}
	public void setFattura(FatturaElettronica fattura) {
		this.fattura = fattura;
	}
	public List<AllegatoFattura> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatoFattura> allegati) {
		this.allegati = allegati;
	}
}
