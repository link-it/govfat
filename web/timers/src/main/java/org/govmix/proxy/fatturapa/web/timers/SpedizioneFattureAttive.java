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
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.EsitoInvioFattura;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.EsitoInvioFattura.ESITO;
import org.govmix.proxy.fatturapa.web.commons.notificaesitocommittente.InvioFattura;
import org.govmix.proxy.fatturapa.web.commons.ricevicomunicazionesdi.RiceviComunicazioneSdI;
import org.govmix.proxy.fatturapa.web.commons.utils.CommonsProperties;

public class SpedizioneFattureAttive implements IWorkFlow<LottoFatture> {

	private Logger log;
	private int limit;
	private LottoFattureAttiveBD lottoFattureAttiveBD;
	private FatturaAttivaBD fatturaAttivaBD;
	private Date limitDate;
	private InvioFattura invioFattura;
	private RiceviComunicazioneSdI riceviComunicazioneSdi;

	@Override
	public void init(Logger log, Connection connection, int limit) throws Exception {
		this.log = log;
		this.limit = limit;
		this.limitDate = new Date();
		this.lottoFattureAttiveBD = new LottoFattureAttiveBD(log, connection, false);
		this.fatturaAttivaBD = new FatturaAttivaBD(log, connection, false);
		this.invioFattura = new InvioFattura(CommonsProperties.getInstance(log).getInvioFatturaURL(), CommonsProperties.getInstance(log).getInvioFatturaUsername(), CommonsProperties.getInstance(log).getInvioFatturaPassword(), log);
		this.riceviComunicazioneSdi = new RiceviComunicazioneSdI(log, connection, false);

	}

	@Override
	public long count() throws Exception {
		return this.lottoFattureAttiveBD.countLottiDaSpedire(this.limitDate);
	}

	@Override
	public List<LottoFatture> getNextLista() throws Exception {
		return this.lottoFattureAttiveBD.getLottiDaSpedire(this.limitDate, 0, this.limit);
	}

	@Override
	public void process(LottoFatture lottoFatture) throws Exception {
		this.log.debug("Elaboro il lotto ["+this.lottoFattureAttiveBD.convertToId(lottoFatture).toJson()+"]");
		
		EsitoInvioFattura esitoInvioFattura = this.invioFattura.invia(lottoFatture);
		StatoElaborazioneType stato = (esitoInvioFattura.getEsito().equals(ESITO.OK)) ? StatoElaborazioneType.SPEDIZIONE_OK : StatoElaborazioneType.ERRORE_SPEDIZIONE;
		this.lottoFattureAttiveBD.updateStatoElaborazioneInUscita(lottoFatture, stato);
		
		if(StatoElaborazioneType.SPEDIZIONE_OK.equals(stato)) {
			TracciaSDI tracciaSdi = new TracciaSDI();
			
			tracciaSdi.setIdentificativoSdi(Integer.parseInt(esitoInvioFattura.getMetadato("X-SDI-IdentificativoSDI")));
			tracciaSdi.setTipoComunicazione(TipoComunicazioneType.FAT_OUT);
			tracciaSdi.setData(new Date());
			tracciaSdi.setContentType(InvioFattura.getContentType(lottoFatture));
			String nomeFile = esitoInvioFattura.getMetadato("X-SDI-NomeFile");
			if(nomeFile == null)
				nomeFile = lottoFatture.getNomeFile();
			
			tracciaSdi.setNomeFile(nomeFile);
			tracciaSdi.setRawData(lottoFatture.getXml());
			
			tracciaSdi.setStatoProtocollazione(StatoProtocollazioneType.PROTOCOLLATA);
			tracciaSdi.setTentativiProtocollazione(0);
			
			tracciaSdi.setIdEgov(esitoInvioFattura.getMetadato(CommonsProperties.getInstance(log).getIdEgovHeader()));
	
			this.riceviComunicazioneSdi.ricevi(tracciaSdi);
			this.lottoFattureAttiveBD.updateIdentificativoSdI(lottoFatture, tracciaSdi.getIdentificativoSdi());
			this.fatturaAttivaBD.assegnaIdentificativoSDIAInteroLotto(this.lottoFattureAttiveBD.convertToId(lottoFatture), tracciaSdi.getIdentificativoSdi());
		}
		
		
		this.log.debug("Elaboro il lotto ["+this.lottoFattureAttiveBD.convertToId(lottoFatture).toJson()+"]: stato ["+lottoFatture.getStatoElaborazioneInUscita()+"] -> ["+stato+"]");
	}

}
