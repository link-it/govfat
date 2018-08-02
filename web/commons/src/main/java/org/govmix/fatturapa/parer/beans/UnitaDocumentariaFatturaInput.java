package org.govmix.fatturapa.parer.beans;
import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.NotificaDecorrenzaTermini;
import org.govmix.proxy.fatturapa.orm.NotificaEsitoCommittente;


public class UnitaDocumentariaFatturaInput extends AbstractUnitaDocumentariaInput {

	private LottoFatture lotto;
	private FatturaElettronica fattura;
	private List<AllegatoFattura> allegati;
	private List<NotificaEsitoCommittente> notificaEC;
	private NotificaDecorrenzaTermini notificaDT;


	public List<AllegatoFattura> getAllegati() {
		if(allegati == null) allegati = new ArrayList<AllegatoFattura>();
		return allegati;
	}
	public List<NotificaEsitoCommittente> getNotificaEC() {
		return notificaEC;
	}
	public void setNotificaEC(List<NotificaEsitoCommittente> notificaEC) {
		this.notificaEC = notificaEC;
	}
	public void setNotificaDT(NotificaDecorrenzaTermini notificaDT) {
		this.notificaDT = notificaDT;
	}
	public NotificaDecorrenzaTermini getNotificaDT() {
		return notificaDT;
	}
	public FatturaElettronica getFattura() {
		return fattura;
	}
	public void setFattura(FatturaElettronica fattura) {
		this.fattura = fattura;
	}
	public LottoFatture getLotto() {
		return lotto;
	}
	public void setLotto(LottoFatture lotto) {
		this.lotto = lotto;
	}
}
