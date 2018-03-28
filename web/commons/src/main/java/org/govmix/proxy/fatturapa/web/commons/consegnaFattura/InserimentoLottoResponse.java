package org.govmix.proxy.fatturapa.web.commons.consegnaFattura;

import java.util.List;

import org.govmix.proxy.fatturapa.orm.IdLotto;

public class InserimentoLottoResponse {

	public enum ESITO {OK, KO}
	
	private ESITO esito;
	private InserimentoLottiException eccezione;
	private List<IdLotto> lstIdentificativoEfatt;
	
	public ESITO getEsito() {
		return esito;
	}
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}
	public InserimentoLottiException getEccezione() {
		return eccezione;
	}
	public void setEccezione(InserimentoLottiException eccezione) {
		this.eccezione = eccezione;
	}
	public List<IdLotto> getLstIdentificativoEfatt() {
		return lstIdentificativoEfatt;
	}
	public void setLstIdentificativoEfatt(List<IdLotto> lstIdentificativoEfatt) {
		this.lstIdentificativoEfatt = lstIdentificativoEfatt;
	}
}
