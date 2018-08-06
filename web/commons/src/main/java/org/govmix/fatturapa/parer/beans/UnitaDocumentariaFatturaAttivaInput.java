package org.govmix.fatturapa.parer.beans;

import java.util.ArrayList;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.AllegatoFattura;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;

public class UnitaDocumentariaFatturaAttivaInput extends AbstractUnitaDocumentariaInput {

	private LottoFatture lotto;
	private FatturaElettronica fattura;
	private List<AllegatoFattura> allegati;
	private List<TracciaSDI> tracce;

	public List<AllegatoFattura> getAllegati() {
		if(allegati == null) allegati = new ArrayList<AllegatoFattura>();
		return allegati;
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
	public List<TracciaSDI> getTracce() {
		return tracce;
	}
	public void setTracce(List<TracciaSDI> tracce) {
		this.tracce = tracce;
	}
}

