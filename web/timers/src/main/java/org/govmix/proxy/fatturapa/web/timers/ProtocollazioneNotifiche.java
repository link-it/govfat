package org.govmix.proxy.fatturapa.web.timers;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.govmix.proxy.fatturapa.orm.LottoFatture;
import org.govmix.proxy.fatturapa.orm.TracciaSDI;
import org.govmix.proxy.fatturapa.orm.constants.StatoElaborazioneType;
import org.govmix.proxy.fatturapa.orm.constants.StatoProtocollazioneType;
import org.govmix.proxy.fatturapa.orm.constants.TipoComunicazioneType;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.FatturaAttivaBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.LottoFattureAttiveBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.TracciaSdIBD;
import org.govmix.proxy.fatturapa.web.commons.businessdelegate.filter.TracciaSdIFilter;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.EsitoInvioFattura;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.EsitoInvioFattura.ESITO;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.InvioFattura;
import org.govmix.proxy.fatturapa.web.commons.ricevicomunicazionesdi.RiceviComunicazioneSdI;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;

public class ProtocollazioneNotifiche implements IWorkFlow<TracciaSDI> {

	private Logger log;
	private int limit;
	private TracciaSdIBD tracciaSdiBD;
	private Date limitDate;

	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.tracciaSdiBD = new TracciaSdIBD(log, connection, false);
	}

	@Override
	public long count() throws Exception {
		return this.tracciaSdiBD.count(this.newFilter());
	}

	private TracciaSdIFilter newFilter() {
		TracciaSdIFilter filter = this.tracciaSdiBD.newFilter();
		filter.setDataProssimaProtocollazioneMin(this.limitDate);
		filter.setStatoProtocollazione(StatoProtocollazioneType.NON_PROTOCOLLATA);
		filter.setOffset(0);
		filter.setLimit(this.limit);
		return filter;
	}

	@Override
	public List<TracciaSDI> getNextLista() throws Exception {
		return this.tracciaSdiBD.findAll(this.newFilter());
	}

	@Override
	public void process(TracciaSDI tracciaSDI) throws Exception {
		this.log.debug("Elaboro la traccia con id ["+tracciaSDI.getId()+"]");
		
//		EsitoInvioFattura esitoInvioFattura = this.invioFattura.invia(tracciaSDI);
//		StatoElaborazioneType stato = (esitoInvioFattura.getEsito().equals(ESITO.OK)) ? StatoElaborazioneType.SPEDIZIONE_OK : StatoElaborazioneType.ERRORE_SPEDIZIONE;
//		this.lottoFattureAttiveBD.updateStatoElaborazioneInUscita(tracciaSDI, stato);
//		
//		if(StatoElaborazioneType.SPEDIZIONE_OK.equals(stato)) {
//			TracciaSDI tracciaSdi = new TracciaSDI();
//			
//			tracciaSdi.setIdentificativoSdi(Integer.parseInt(esitoInvioFattura.getMetadato("X-SDI-IdentificativoSDI")));
//			tracciaSdi.setTipoComunicazione(TipoComunicazioneType.FATTURA_USCITA);
//			tracciaSdi.setData(new Date());
//			tracciaSdi.setContentType(InvioFattura.getContentType(tracciaSDI));
//			String nomeFile = esitoInvioFattura.getMetadato("X-SDI-NomeFile");
//			if(nomeFile == null)
//				nomeFile = tracciaSDI.getNomeFile();
//			
//			tracciaSdi.setNomeFile(nomeFile);
//			tracciaSdi.setRawData(tracciaSDI.getXml());
//			
//			tracciaSdi.setStatoProtocollazione(StatoProtocollazioneType.PROTOCOLLATA);
//			tracciaSdi.setTentativiProtocollazione(0);
//			
//			tracciaSdi.setIdEgov(esitoInvioFattura.getMetadato(CommonsProperties.getInstance(log).getIdEgovHeader()));
//	
//			this.riceviComunicazioneSdi.ricevi(tracciaSdi);
//			this.lottoFattureAttiveBD.updateIdentificativoSdI(tracciaSDI, tracciaSdi.getIdentificativoSdi());
//			this.tracciaSdiBD.assegnaIdentificativoSDIAInteroLotto(this.lottoFattureAttiveBD.convertToId(tracciaSDI), tracciaSdi.getIdentificativoSdi());
//		}
		
		
//		this.log.debug("Elaboro la traccia con id ["+tracciaSDI.getId()+"]: stato protocollazione ["+tracciaSDI.getStatoProtocollazione()+"] -> ["+stato+"]");
	}

}
