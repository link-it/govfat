package org.govmix.fatturapa.parer.beans;
import java.util.List;

import org.govmix.proxy.fatturapa.orm.LottoFatture;


public class UnitaDocumentariaLottoInput extends AbstractUnitaDocumentariaInput {

	private LottoFatture lotto;
	private List<FatturaBean> fattureLst;
	public List<FatturaBean> getFattureLst() {
		return fattureLst;
	}
	public void setFattureLst(List<FatturaBean> fattureLst) {
		this.fattureLst = fattureLst;
	}
	public LottoFatture getLotto() {
		return lotto;
	}
	public void setLotto(LottoFatture lotto) {
		this.lotto = lotto;
	}
}
