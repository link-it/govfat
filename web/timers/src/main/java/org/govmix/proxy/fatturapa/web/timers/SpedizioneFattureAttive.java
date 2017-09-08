package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;

public class SpedizioneFattureAttive implements IWorkFlow {

	private Logger log;
	private int limit;
	private FatturaAttivaBD fatturaAttivaBD;
	private Date limitDate;
	
	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.fatturaAttivaBD = new FatturaAttivaBD(log, connection, false);
	}

	@Override
	public long count() throws Exception {
		FatturaAttivaFilter filter = this.getFilter();
		return this.fatturaAttivaBD.count(filter);
	}

	@Override
	public List<FatturaElettronica> getNextListaFatture() throws Exception {
		FatturaAttivaFilter filter = this.getFilter();
		filter.setOffset(0);
		filter.setLimit(this.limit);
		return this.fatturaAttivaBD.findAll(filter);
	}

	private FatturaAttivaFilter getFilter() {
		FatturaAttivaFilter filter = this.fatturaAttivaBD.newFilter();
		filter.getStatoElaborazioneList().add(StatoElaborazioneType.PROTOCOLLAZIONE_OK);
		filter.setDataUltimaElaborazioneMax(this.limitDate);
		return filter;
	}

	@Override
	public void process(FatturaElettronica fattura) throws Exception {
		this.log.debug("Elaboro la fattura ["+this.fatturaAttivaBD.convertToId(fattura).toJson()+"]");
		boolean cond = Math.random() < 0.5d;
		StatoElaborazioneType stato = (cond) ? StatoElaborazioneType.SPEDIZIONE_OK : StatoElaborazioneType.ERRORE_SPEDIZIONE;
		this.fatturaAttivaBD.updateStatoElaborazioneInUscita(fattura, stato);
		this.log.debug("Elaboro la fattura ["+this.fatturaAttivaBD.convertToId(fattura).toJson()+"]: stato ["+fattura.getLottoFatture().getStatoElaborazioneInUscita()+"] -> ["+stato+"]");
	}

}
