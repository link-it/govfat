package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.FatturaElettronica;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.FatturaAttivaFilter;

public class WorkFlow implements IWorkFlow {

	private Logger log;
	private int limit;
	private FatturaAttivaBD fatturaAttivaBD;
	private Date limitDate;
//	private EndpointSelector endpointSelector;
	
	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.fatturaAttivaBD = new FatturaAttivaBD(log, connection, false);
//		this.endpointSelector = new EndpointSelector(log, connection, true);
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
		filter.getStatoElaborazioneList().add(StatoElaborazioneType.NON_FIRMATO);
		filter.getStatoElaborazioneList().add(StatoElaborazioneType.FIRMA_OK);
		filter.setDataUltimaElaborazioneMax(this.limitDate);
		return filter;
	}

	@Override
	public void process(FatturaElettronica fattura) throws Exception {
		StatoElaborazioneType statoElaborazioneInUscita = fattura.getLottoFatture().getStatoElaborazioneInUscita();

		this.log.debug("Elaboro la fattura ["+this.fatturaAttivaBD.convertToId(fattura).toJson()+"]");
		if(StatoElaborazioneType.NON_FIRMATO.equals(statoElaborazioneInUscita)) {
			this.fatturaAttivaBD.updateStatoElaborazioneInUscita(fattura, StatoElaborazioneType.FIRMA_IN_PROGRESS);
			this.log.debug("Elaboro la fattura ["+this.fatturaAttivaBD.convertToId(fattura).toJson()+"], stato ["+statoElaborazioneInUscita+"] -> ["+StatoElaborazioneType.FIRMA_IN_PROGRESS+"]");
		} else if(StatoElaborazioneType.FIRMA_OK.equals(statoElaborazioneInUscita)) {
			this.fatturaAttivaBD.updateStatoElaborazioneInUscita(fattura, StatoElaborazioneType.PROTOCOLLAZIONE_IN_PROGRESS);
			this.log.debug("Elaboro la fattura ["+this.fatturaAttivaBD.convertToId(fattura).toJson()+"], stato ["+statoElaborazioneInUscita+"] -> ["+StatoElaborazioneType.PROTOCOLLAZIONE_IN_PROGRESS+"]");
		}
	}

}
